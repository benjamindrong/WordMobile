package com.benjamindrong.wordmobile

// Declares the DAO as a private property in the constructor. Pass in the DAO
// instead of the whole database, because you only need access to the DAO
class WordRepository (private val dao: WordDao) {



}