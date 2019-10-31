package com.rigobertocanseco.sudokuscala

import util.control.Breaks._

class Sudoku {
    private val size: Int = 9

    def Sudoku(): Unit = {
    }

    def create(): Array[Array[Int]] = {
        val array = fill(size)
        addNumberRandom(1 + scala.util.Random.nextInt(size), Array(0,0), array)
        array
    }

    def resolver(a: Array[Array[Int]]): Array[Array[Int]] = {
        backTrack(a)
        a
    }

    def show(a: Array[Array[Int]]): Unit = {
        println(" =========================================")
        for (i <- 0 until size) {
            for (j<- 0 until size) {
                if(j%3 == 0)
                    print(s" || ${if(a(i)(j) == 0) "*" else a(i)(j)}")
                else  print(s" | ${if(a(i)(j) == 0) "*" else a(i)(j)}")
            }
            print(" || ")
            println()
            if((i+1)%3 == 0)
                println(" =========================================")
        }
    }

    private def fill(size:Int): Array[Array[Int]] = Array.fill(size,size)(0)

    private def empty(a:Array[Array[Int]]):Boolean = !(for (x <- 0 until size; y <- 0 until size) yield a(x)(y)).contains(0)

    private def row(a:Array[Array[Int]], x:Int) :Array[Int] = (for (i <- 0 until size) yield a(x)(i)).toArray

    private def column(a:Array[Array[Int]], y:Int) :Array[Int] = (for (i <- 0 until size) yield a(i)(y)).toArray

    private def cell(a:Array[Array[Int]], x1:Int, y1:Int, x2:Int, y2:Int): Array[Int] =
        (for (x <- x1 to x2; y <- y1 to y2) yield a(x)(y)).toArray

    private def cell(a: Array[Array[Int]], x:Int, y:Int): Array[Int] =
        (for ( x1 <- (x/3 * 3) to x/3 * 3 + 2; y1 <- y/3 * 3 to y/3 *3 + 2) yield a(x1)(y1)).toArray

    private def availableNumbers(a:Array[Array[Int]], x:Int, y:Int): Array[Int] =
        (1 to size).diff((row(a, x) ++ column(a, y) ++ cell(a, x, y)).distinct).toArray

    private def possibleNumbers(a: Array[Array[Int]]): Array[Array[Array[Int]]] =
        (for(x<-0 until size; y <- 0 until size) yield if (a(x)(y) == 0) availableNumbers(a, x, y) else Array(0)).toArray.grouped(size).toArray

    private def possibleNumbersOrder(a: Array[Array[Int]]): Array[Array[Array[Int]]] = {
        var p: Array[Array[Array[Int]]] = Array[Array[Array[Int]]]()
        for( (v, x) <- possibleNumbers(a).zipWithIndex; (v1, y) <- v.zipWithIndex if v1.length>=1 && v1(0)!=0)
            p +:= Array(Array(x, y), v1)
        p
    }

    private def addNumberRandom(i:Int, pos:Array[Int], a: Array[Array[Int]]): Array[Array[Int]] = {
        if (!empty(a)) {
            if(availableNumbers(a, pos(0), pos(1)).length == 0) {
                for (i<- 0 to 8; j<- 0 to 8) a(i)(j) = 0
                pos(0) = 0
                pos(1) = 0
            }
            val l = availableNumbers(a, pos(0), pos(1))
            val n = l(scala.util.Random.nextInt(l.length))

            if (!row(a, pos(0)).contains(i) && !column(a, pos(1)).contains(i) && !cell(a, pos(0), pos(1)).contains(i)) {
                a(pos(0))(pos(1)) = i
                addNumberRandom(n, if (pos(0) < 8) Array(pos(0) + 1, pos(1)) else Array(0, pos(1) + 1), a)
            } else
                addNumberRandom(n, pos, a)
        }
        else
            new Array[Array[Int]](0)
    }

    private def backTrack(a: Array[Array[Int]]): Boolean = {
        val l = possibleNumbersOrder(a).sortBy(_(1).length)
        if(l.length > 0 ){
            breakable {
                for( e <- l; n <- e(1)) {
                    val va = a(e(0)(0))(e(0)(1))
                    a(e(0)(0))(e(0)(1)) = n
                    if(backTrack(a)) break
                    else {
                        a(e(0)(0))(e(0)(1)) = va
                        false
                    }
                }
            }
        }
        true
    }

}
