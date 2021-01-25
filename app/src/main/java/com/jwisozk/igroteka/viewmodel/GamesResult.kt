package com.jwisozk.igroteka.viewmodel

import com.jwisozk.igroteka.model.Game

sealed class GamesResult
class ValidResult(val result: List<Game>) : GamesResult()
object EmptyResult : GamesResult()
object EmptyQuery : GamesResult()
object ErrorResult : GamesResult()