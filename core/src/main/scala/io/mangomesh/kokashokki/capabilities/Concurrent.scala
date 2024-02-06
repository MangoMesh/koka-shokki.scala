/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki.capabilities

import io.mangomesh.kokashokki.With

import java.util.concurrent.CompletableFuture

object Concurrent {
  class Awaitable[A](val future: CompletableFuture[A]) extends Capability[A] {}

  /* Disabled, since the compiler is strugling to understand that ofVirtual is a method of Thread */
  /*
  def start[C[_] <: Capability[?], A](f: => With[C, A]): With[Awaitable, With[C, A]] = {
    val future = new CompletableFuture[With[C, A]]()
     Thread.ofVirtual().start { () =>
      val result = f
      future.complete(result)
     }
    new With[Awaitable, With[C, A]] {
      val value = Right(future.get())
    }
  }
   */
}
