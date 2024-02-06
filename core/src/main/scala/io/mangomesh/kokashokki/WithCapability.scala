/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki

import io.mangomesh.kokashokki.capabilities.Capability
import jdk.internal.vm.Continuation
import jdk.internal.vm.ContinuationScope

object WithCapability {
  def apply[C[_] <: Capability[?], A](f: Scope[C] ?=> A): With[C, A] = {
    var r: With[C, A] = null.asInstanceOf[With[C, A]]
    val scope: ContinuationScope = new ContinuationScope("WithCapability")
    val scopeInstance: Scope[C] = new Scope {
      override def ko[B, K[_] <: Capability[?]](
          x: With[K, B]
      )(implicit ev: K[B] <:< C[B]): B = {
        r = x.asInstanceOf[
          With[C, A]
        ] // The cast is safe because of the proof from the implicit parameter
        Continuation.`yield`(scope)
        throw new RuntimeException("unreachable")
      }

    }
    val cont: Continuation = new Continuation(
      scope,
      () => {
        val result = f(using scopeInstance)
        r = new With[C, A] {
          val value = Right(result)
        }
        Continuation.`yield`(scope)
        ()
      }
    )
    cont.run
    r
  }
}
