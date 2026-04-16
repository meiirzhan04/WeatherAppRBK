package edu.learn.weatherapprbk.core.mapper

fun interface BaseMapper<FROM, TO> {
    fun map(source: FROM): TO
}

interface BiMapper<E, D> : BaseMapper<E, D> {
    fun reverse(source: D): E
}