/*
 * Copyright 2023 mangomesh
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.mangomesh.kokashokki

import io.mangomesh.kokashokki.Koka.runEffect
import io.mangomesh.kokashokki.capabilities.LeftChannel.asLeft
import io.mangomesh.kokashokki.capabilities.LeftChannel.ok
import io.mangomesh.kokashokki.capabilities.Lefted
import io.mangomesh.kokashokki.capabilities.Optional
import io.mangomesh.kokashokki.syntax.*
import weaver.*

import scala.language.implicitConversions

object MainSuite extends SimpleIOSuite {

  def divide(
      x: With[Optional, Int],
      y: With[Optional, Int]
  ): Int <~ Optional :| Lefted[String] =
    WithCapability[Optional :| Lefted[String], Int] {
      val result: With[Lefted[String], Int] =
        if (y.? == 0) asLeft("division by zero") else ok(x.? / y.?)
      result.?
    }

  def runDivEffect(
      x: With[Optional :| Lefted[String], Int]
  ): Either[String, Int] = {
    runEffect(x) {
      case Left(channel) =>
        channel match {
          case _: Optional[Int] => Left("The value is not present")
          case error: Lefted[String][Int] =>
            Left(error.err)
        }
      case Right(value) => Right(value)
    }
  }

  pureTest("division by zero") {
    val x = divide(Some(1), Some(0))
    val result = runDivEffect(x)

    expect(result == Left("division by zero"))
  }

  pureTest("division with None") {
    val x = divide(Some(1), None)
    val result = runDivEffect(x)

    expect(result == Left("The value is not present"))
  }

}
