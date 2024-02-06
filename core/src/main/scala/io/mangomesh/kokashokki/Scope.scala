/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki

import io.mangomesh.kokashokki.capabilities.Capability

trait Scope[-C[_] <: Capability[?]] { self =>
  def ko[A, K[_] <: Capability[?]](x: With[K, A])(implicit ev: K[A] <:< C[A]): A
}
