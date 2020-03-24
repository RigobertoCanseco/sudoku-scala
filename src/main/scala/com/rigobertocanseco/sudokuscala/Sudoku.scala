package com.rigobertocanseco.sudokuscala

import scala.collection.mutable.ArrayBuffer
import util.control.Breaks._

class Sudoku {
    private val SIZE: Int = 9
    private val SIZE_SQRT: Int = (scala.math.sqrt(SIZE)).toInt

    def Sudoku(): Unit = {}

    /**
     * Create a sudoku
     * @return Array[Int][Int]
     */
    def create(): Array[Array[Int]] = {
        val array = Array.fill(SIZE, SIZE)(0)
        addNumberRandom(1 + scala.util.Random.nextInt(SIZE), Array(0,0), array)
        array
    }

    def resolver(a: Array[Array[Int]]): Array[Array[Int]] = {
        //backTrack(a)

        val l = allPossibleNumbers(a)
        //val l = possibleNumbersOrder(a)

        for((v1, x) <- l.zipWithIndex; (v2, y) <- v1.zipWithIndex){
            println(s"($x,$y) => ${v2.view.mkString(",")}")
        }
        println("una solucion")
        val q = onlyPossibleNumber(a)

        val a1 = join(a, q)

        show(a1)

        //println(a.map(_.map(_.equals(0))).view)

        //for (i <- 0 until SIZE; j<- 0 until SIZE) yield if(q(i)(j) != 0) a(i)(j) = q(i)(j)


        //show(a)

        val m = groupNumbers(a)
        for((k, v)<-m)
            println(s"$k = { ${ for( e <- v) yield e.view} }")
        println("mapping")
        val s = mappingNumber(a, 1)
        for(e <- s) println(s"${e.view}")

        a
    }

    def show(a: Array[Array[Int]]): Unit = {
        println(" =========================================")
        for (i <- 0 until SIZE) {
            for (j<- 0 until SIZE) {
                if(j%SIZE_SQRT == 0)
                    print(s" || ${if(a(i)(j) == 0) "*" else a(i)(j)}")
                else  print(s" | ${if(a(i)(j) == 0) "*" else a(i)(j)}")
            }
            print(" || ")
            println()
            if((i+1)%SIZE_SQRT == 0)
                println(" =========================================")
        }
    }

    /**
     * If a array contain '0' return false else return true
     * @param a Sudoku grid Array[Int][Int]
     * @return Boolean
     */
    private def empty(a:Array[Array[Int]]):Boolean = !(for (x <- 0 until SIZE; y <- 0 until SIZE) yield a(x)(y)).contains(0)

    /**
     * Get a row of x position of a sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @param x Position
     * @return Array[Int]
     */
    private def row(a:Array[Array[Int]], x:Int) :Array[Int] = (for (i <- 0 until SIZE) yield a(x)(i)).toArray

    /**
     * Get a column of y position of a sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @param y Position
     * @return Array[Int]
     */
    private def column(a:Array[Array[Int]], y:Int) :Array[Int] = (for (i <- 0 until SIZE) yield a(i)(y)).toArray

    private def cell(a:Array[Array[Int]], x1:Int, y1:Int, x2:Int, y2:Int): Array[Int] =
        (for (x <- x1 to x2; y <- y1 to y2) yield a(x)(y)).toArray

    /**
     * Get a mini grid of column x and row y of a sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @param x Column
     * @param y Row
     * @return Array[Int][Int]
     */
    private def miniGrid(a: Array[Array[Int]], x:Int, y:Int): Array[Int] =
        (for ( x1 <- (x/SIZE_SQRT * SIZE_SQRT) to x/SIZE_SQRT * SIZE_SQRT + 2;
               y1 <- y/SIZE_SQRT * SIZE_SQRT to y/SIZE_SQRT *SIZE_SQRT + 2) yield a(x1)(y1)).toArray

    /**
     * Return all numbers available in sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @param x Column
     * @param y Row
     * @return Array[Int]
     */
    private def availableNumbers(a:Array[Array[Int]], x:Int, y:Int): Array[Int] =
        (1 to SIZE).diff((row(a, x) ++ column(a, y) ++ miniGrid(a, x, y)).distinct).toArray

    /**
     * Return all possible numbers in a one cell of all sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @return Array[Int][Int,Int]
     */
    private def allPossibleNumbers(a: Array[Array[Int]]): Array[Array[Array[Int]]] =
        (for(x<-0 until SIZE; y <- 0 until SIZE) yield if (a(x)(y) == 0) availableNumbers(a, x, y) else Array(0))
            .toArray.grouped(SIZE).toArray

    /**
     * Sudoku grid join with a solved sudoku grid
     * @param a Sudoku grid Array[Int][Int]
     * @param r Solved sudoku grid Array[Int][Int]
     * @return Array[Int][Int]
     */
    private def join(a: Array[Array[Int]], r:Array[Array[Int]]): Array[Array[Int]] =
        a.zip(r).map { case (x, y) => x.zip(y).map { case(i,j) => i + j } }

    private def onlyPossibleNumber(a: Array[Array[Int]]): Array[Array[Int]] = {
        (for(x<-0 until SIZE; y <- 0 until SIZE) yield
            if (a(x)(y) == 0){
                val pn = availableNumbers(a, x, y)
                    if(pn.length == 1 && pn(0) !=0)
                        pn(0)
                else 0
            } else 0
        ).toArray.grouped(SIZE).toArray
    }

    private def possibleNumbersOrder(a: Array[Array[Int]]): Array[Array[Array[Int]]] = {
        var p: Array[Array[Array[Int]]] = Array[Array[Array[Int]]]()
        for( (v, x) <- allPossibleNumbers(a).zipWithIndex; (v1, y) <- v.zipWithIndex if v1.length>0 && v1(0)!=0)
            p +:= Array(Array(x, y), v1)
        p
    }

    private def findNumber(a:Array[Array[Int]], i:Int): Array[Array[Int]] =
        (for(x<-0 until SIZE; y <- 0 until SIZE; if a(x)(y) == i) yield Array(x,y)).toArray

    private def emptyCells(a: Array[Array[Int]]): Array[Array[Int]] =
        (for(x<-0 until SIZE; y <- 0 until SIZE; if a(x)(y) == 0) yield Array(x, y)).toArray

    //private def cell(a: Array[Array[Int]], x:Int, y:Int): Array[Array[Int]] =
    //    (for ( x1 <- (x/3 * 3) to x/3 * 3 + 2; y1 <- y/3 * 3 to y/3 *3 + 2) yield a(x1)(y1))

    private def mappingNumber(a: Array[Array[Int]], i: Int): Array[Array[Int]] = {
        val n = findNumber(a, 7)

        var e = emptyCells(a)

        for(m <- n)
            e = e.filter( i => i(0) != m(0) && i(1) != m(1))
        e
    }


    private def groupNumbers(a:Array[Array[Int]]): collection.mutable.Map[Int, ArrayBuffer[Array[Int]]] = {
        val m = collection.mutable.Map[Int, ArrayBuffer [Array[Int]]]()

        for( i <- 1 to SIZE) {
            val ar = findNumber(a, i)
            println(s"$i")
            for(e <- ar) println(s"${e.view}")
//            println(s"($x,$y) => $v1")
//            if(!m.contains(i))
//                m(i) = ArrayBuffer(Array(x, y))
//            else
//                m(i) +:= Array(x, y)
        }
        m
    }


    /**
     *
     * @param i
     * @param pos
     * @param a sudoku grid Array[ Array[ Int ] ]
     * @return
     */
    @scala.annotation.tailrec
    private def addNumberRandom(i:Int, pos:Array[Int], a: Array[Array[Int]]): Array[Array[Int]] = {
        if (!empty(a)) {
            if(availableNumbers(a, pos(0), pos(1)).length == 0) {
                for (i<- 0 until SIZE; j<- 0 until SIZE) a(i)(j) = 0
                pos(0) = 0
                pos(1) = 0
            }
            val l = availableNumbers(a, pos(0), pos(1))
            val n = l(scala.util.Random.nextInt(l.length))

            if (!row(a, pos(0)).contains(i) && !column(a, pos(1)).contains(i) && !this.miniGrid(a, pos(0), pos(1)).contains(i)) {
                a(pos(0))(pos(1)) = i
                addNumberRandom(n, if (pos(0) < 8) Array(pos(0) + 1, pos(1)) else Array(0, pos(1) + 1), a)
            } else
                addNumberRandom(n, pos, a)
        }
        else
            new Array[Array[Int]](0)
    }

    private def gridEmpty(a: Array[Array[Int]]): Boolean = a.iterator.flatMap(_.iterator).filter(_ == 0).iterator.isEmpty

    private def backTrack(a: Array[Array[Int]]): Boolean = {
        show(a)
        val l = possibleNumbersOrder(a).sortBy(_(1).length)
        if (l.length > 0) {
            breakable {
                for (e <- l; n <- e(1)) {
                    val va = a(e(0)(0))(e(0)(1))
                    a(e(0)(0))(e(0)(1)) = n
                    if (backTrack(a) && gridEmpty(a)) {
                        show(a)
                        break
                    }
                    else {
                        a(e(0)(0))(e(0)(1)) = va
                        false
                    }
                }
            }
        }

        true
    }

//    private def backTrack(a: Array[Array[Int]]): Boolean = {
//        for (x <- 0 until size; y <- 0 until size) {
//            if (a(x)(y) == 0){
//                for (i <- availableNumbers(a, x, y)){
//                    a(x)(y) = i
//                    if(backTrack(a)) {
//                        return true
//                    }
//
//
//                }
//                a(x)(y) = 0
//                false
//            }else{
//                show(a)
//
//            }
//        }
//
//        true
//    }
}
