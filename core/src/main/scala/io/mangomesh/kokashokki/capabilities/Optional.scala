/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki.capabilities

import io.mangomesh.kokashokki.With

class Optional[A] extends Capability[A] {}

object Optional {
  given conversion[A]: Conversion[Option[A], With[Optional, A]] =
    new Conversion[Option[A], With[Optional, A]] {
      override def apply(x: Option[A]): With[Optional, A] = x match {
        case Some(x) =>
          new With[Optional, A] {
            val value = Right(x)
          }
        case None =>
          new With[Optional, A] {
            val value = Left(new Optional[A] {})
          }
      }
    }

  extension [A](x: Option[A]) def lift: With[Optional, A] = conversion(x)
}
