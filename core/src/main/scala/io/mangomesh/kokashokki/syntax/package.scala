/*
 * Copyright (c) 2024 mangomesh ~ ivanmoreau
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0. If a copy of the MPL was not distributed with this
 * file, You can obtain one at http://mozilla.org/MPL/2.0/.
 */

package io.mangomesh.kokashokki

import io.mangomesh.kokashokki.capabilities.Capability

package object syntax {
  type :|[C1[_] <: Capability[?], C2[_] <: Capability[?]] = [A] =>> C1[A] |
    C2[A]
  type <~[A, C[_] <: Capability[?]] = With[C, A]
}
