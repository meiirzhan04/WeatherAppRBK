package edu.learn.weatherapprbk.core.mapper

interface BaseMapper<I, O> {
    fun map(input: I): O
}