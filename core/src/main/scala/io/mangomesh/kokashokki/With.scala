/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki

import io.mangomesh.kokashokki.capabilities.Capability

trait With[C[_] <: Capability[?], A] { self =>
  val value: Either[C[A], A]

  def ?(using scope: Scope[C]): A = value match {
    case Left(channel) => scope.ko(self)
    case Right(value)  => value
  }
}
