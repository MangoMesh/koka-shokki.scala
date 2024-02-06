/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki.capabilities

import io.mangomesh.kokashokki.With

class LeftChannel[Err, A](val err: Err) extends Capability[A] {}

object LeftChannel {
  def ok[A](x: A): With[Lefted[String], A] = new With[Lefted[String], A] {
    val value = Right(x)
  }
  def asLeft[A](x: String): With[Lefted[String], A] =
    new With[Lefted[String], A] {
      val value = Left(new Lefted(x))
    }
}
