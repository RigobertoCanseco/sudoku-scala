package com.rigobertocanseco.sudokuscala

case class Cell(v: Int, o: Array[Int]) {
  def value: Int = if(o.length == 1) o(0) else v
  def options: Array[Int] = if(o.length == 1) Array() else o
}

case class Row(c: Array[Cell]) {
  def cells: Array[Cell] = c
}

case class Column(c: Array[Cell]) {
  def cells: Array[Cell] = c
}

case class MiniGrid(c: Array[Cell]) {
  def cells: Array[Cell] = c
}

class Sudoku(){
  val size: Int = 9
  val size_sqrt: Int = (scala.math.sqrt(size)).toInt
  var grid: Grid = _

  def create(array: Array[Array[Int]]): Unit = {
    this.grid = new Grid(array)
  }

  //def getSolutions: Iterable[Grid] = solver(this.grid)

//  def solver(grid: Grid): Iterable[Grid] = {
//    val nextGrid: Grid = grid.reduce()
//
//    if(!nextGrid.equals(grid)) {
//      Array(nextGrid) ++ solver(nextGrid)
//    } else {
//      Array(nextGrid)
//    }
//  }

  def printGrid(): Unit = {
    println(this.grid.toString)
  }

//  def _print(): Unit = {
//    println(" =========================================")
//    for (i <- 0 until size) {
//      for (j <- 0 until size) {
//        if (j % 3 == 0)
//          print(s" || ${if (cells(i)(j).value == 0) "*" else cells(i)(j).value}")
//        else print(s" | ${if (cells(i)(j).value == 0) "*" else cells(i)(j).value}")
//      }
//      print(" || ")
//      println()
//      if ((i + 1) % 3 == 0)
//        println(" =========================================")
//    }
//    println(cells.map(e=>e.map(e=>e.options)).iterator.flatMap(_.iterator).filter(_ == 1).iterator.size)
//    println(cells.flatMap(_.iterator).filter(_.options.length>0).sortWith(_.options.length < _.options.length).view)
//    //cells.flatMap(_.iterator).filter(_.options.length>0)
//    //cells.flatMap(e => e.flatMap(e=>e.options)).iterator
//    //.iterator.flatMap(_.iterator).filter(_ == 1).iterator.size
//    for (i <- 0 until size) {
//      for (j <- 0 until size) {
//        println(cells(i)(j).value, cells(i)(j).options.view)
//      }
//    }
//
//  }
}


import scala.collection.mutable.ArrayBuffer
//import scala.util.control.Breaks._

//class Grid(a: Array[Array[Int]]) {
//  final val size: Int = 9
//  final val size_sqrt: Int = (scala.math.sqrt(size)).toInt
//  /*
//     (for (x1 <- (x / SIZE_SQRT * SIZE_SQRT) to x / SIZE_SQRT * SIZE_SQRT + 2;
//          y1 <- y / SIZE_SQRT * SIZE_SQRT to y / SIZE_SQRT * SIZE_SQRT + 2) yield a(x1)(y1)).toArray
//   */
//  val cells: Array[Array[Cell]] =
//      (for (row <- 0 until size; column <- 0 until size) yield
//      if (a(row)(column) == 0)
//        Cell(0, (1 to size).diff((
//          (for (i <- 0 until size) yield a(row)(i)) ++
//          (for (i <- 0 until size) yield a(i)(column)) ++
//          (for (r <- (row / size_sqrt * size_sqrt) to row / size_sqrt * size_sqrt + 2;
//                c <- column / size_sqrt * size_sqrt to column / size_sqrt * size_sqrt + 2) yield a(r)(c)))
//          .distinct).toArray)
//      else
//        Cell(a(row)(column), Array())
//      ).toArray.grouped(size).toArray
//
////    (for (x <- 0 until size; y <- 0 until size) yield
////      if (grid(x)(y).value == 0)
////        Cell(0, (1 to size).diff((
////          (for (i <- 0 until size) yield cells(x)(i).value)
////          ++ (for (i <- 0 until size) yield cells(i)(y).value)
////          ++ (for (r <- (x / size) to x / size + 2; c <- y / size to y / size + 2) yield cells(r)(c)))
////          .distinct).toArray)
////      else Cell(grid(x)(y).value, Array(0)))
////      .toArray.grouped(size).toArray
//
//  def reduce(): Grid = {
//    var v:Int = 0
//    new Grid(
//      (for (x <- 0 until size; y <- 0 until size) yield {
//        v = 0
//        if (cells(x)(y).options.length == 0)
//          cells(x)(y).value
//        else {
//          (1 to size).foreach {
//            i => {
//              if (cells(x)(y).options.contains(i) &&
//                ((for (i <- 0 until size) yield
//                  cells(x)(i).options).toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
//                  (for (i <- 0 until size) yield
//                    cells(i)(y).options).toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
//                  (for (x1 <- (x / size_sqrt * size_sqrt) to x / size_sqrt * size_sqrt + 2;
//                        y1 <- y / size_sqrt * size_sqrt to y / size_sqrt * size_sqrt + 2)
//                    yield cells(x1)(y1).options).toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
//                v = i
//            }
//          }
//          v
//        }
//        }).toArray.grouped(size).toArray)
//  }
//
//  def _print(): Unit = {
//    println(" =========================================")
//    for (i <- 0 until size) {
//      for (j <- 0 until size) {
//        if (j % 3 == 0)
//          print(s" || ${if (cells(i)(j).value == 0) "*" else cells(i)(j).value}")
//        else print(s" | ${if (cells(i)(j).value == 0) "*" else cells(i)(j).value}")
//      }
//      print(" || ")
//      println()
//      if ((i + 1) % 3 == 0)
//        println(" =========================================")
//    }
//    for (i <- 0 until size) {
//      for (j <- 0 until size) {
//        println(cells(i)(j).value, cells(i)(j).options.view)
//      }
//    }
//
//  }
////  def getCell(row: Int, col: Int): Cell = cells(row)(col)
//
////  def getRow(row: Int): Row = Row((for (i <- 0 until size) yield cells(row)(i)).toArray)
//
////  def getColumn(column: Int): Column = Column((for (i <- 0 until size) yield cells(i)(column)).toArray)
//
////  def getMiniGrid(row: Int, column: Int): MiniGrid =
////    MiniGrid((for (r <- (row / size) to row / size + 2; c <- column / size to column / size + 2) yield cells(r)(c)).toArray)
//
////  def availableNumbers(x: Int, y: Int): Array[Int] =
////    (1 to size).diff((getRow(x).cells ++ getColumn(y).cells ++ getMiniGrid(x, y).cells).distinct).toArray
//
//}

//class Sudoku {
//  private val SIZE: Int = 9
//  private val SIZE_SQRT: Int = (scala.math.sqrt(SIZE)).toInt
//  private var grid: Grid = _
//
//  def Sudoku(): Unit = {}
//
//  /**
//   * Create a sudoku
//   *
//   * @return Array[Int][Int]
//   */
//  def create(): Array[Array[Int]] = {
//    val array = Array.fill(SIZE, SIZE)(0)
//    addNumberRandom(1 + scala.util.Random.nextInt(SIZE), Array(0, 0), array)
//    array
//  }
//
//  def create(array: Array[Array[Int]]): Unit = {
//    this.grid = new Grid(array)
//  }
//
////  def resolver(): Grid = {
////    val r = reduce(allPossibleNumbers(a))
////
////    if (haveSolutions(r)) {
////      resolver(join(a, r))
////    } else a
////  }
//
//  def printGrid(): Unit = {
//    this.grid._print()
//  }
//
//  def selectCell(a: Array[Array[Int]], r: Int, c: Int): Array[Array[Int]] = {
//    val g: Array[Array[Int]] = Array()
//    row(a, r).zipWithIndex.foreach(e => print(e))
//    println("")
//    column(a, c).zipWithIndex.foreach(e => print(e))
//    println("")
//    miniGrid(a, 0, 0).zipWithIndex.foreach(e => print(e))
//
//
//    g
//  }
//
//  //!(for (x <- 0 until SIZE; y <- 0 until SIZE) yield a(x)(y)).contains(0)
//
////  def possibleNumbersOrder(a: Array[Array[Int]]): Array[Array[Array[Int]]] = {
////    var p: Array[Array[Array[Int]]] = Array[Array[Array[Int]]]()
////    for ((v, x) <- allPossibleNumbers(a).zipWithIndex; (v1, y) <- v.zipWithIndex if v1.length > 0 && v1(0) != 0)
////      p +:= Array(Array(x, y), v1)
////    p
////  }
//
//
////  def allPossibleNumbers(): Array[Array[Array[Int]]] =
////    (for (x <- 0 until SIZE; y <- 0 until SIZE) yield if (a(x)(y) == 0) availableNumbers(a, x, y) else Array(0))
////      .toArray.grouped(SIZE).toArray
//
//  /**
//   * Return all numbers available in sudoku grid
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @param x Column
//   * @param y Row
//   * @return Array[Int]
//   */
//  private def availableNumbers(a: Array[Array[Int]], x: Int, y: Int): Array[Int] =
//    (1 to SIZE).diff((row(a, x) ++ column(a, y) ++ miniGrid(a, x, y)).distinct).toArray
//
//  /**
//   * Get a row of x position of a sudoku grid
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @param r Row
//   * @return Array[Int]
//   */
//  private def row(a: Array[Array[Int]], r: Int): Array[Int] = (for (i <- 0 until SIZE) yield a(r)(i)).toArray
//
//  /**
//   * Get a column of y position of a sudoku grid
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @param y Position
//   * @return Array[Int]
//   */
//  private def column(a: Array[Array[Int]], y: Int): Array[Int] = (for (i <- 0 until SIZE) yield a(i)(y)).toArray
//
//  /**
//   * Get a mini grid of column x and row y of a sudoku grid
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @param x Column
//   * @param y Row
//   * @return Array[Int][Int]
//   */
//  private def miniGrid(a: Array[Array[Int]], x: Int, y: Int): Array[Int] =
//    (for (x1 <- (x / SIZE_SQRT * SIZE_SQRT) to x / SIZE_SQRT * SIZE_SQRT + 2;
//          y1 <- y / SIZE_SQRT * SIZE_SQRT to y / SIZE_SQRT * SIZE_SQRT + 2) yield a(x1)(y1)).toArray
//
//  /**
//   * If a array contain '0' return false else return true
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @return Boolean
//   */
//  private def empty(a: Array[Array[Int]]): Boolean = a.iterator.flatMap(_.iterator).filter(_ == 0).iterator.isEmpty
//
//  private def haveSolutions(a: Array[Array[Int]]): Boolean = a.iterator.flatMap(_.iterator).filter(_ != 0).iterator.nonEmpty
//
//  /**
//   *
//   * @param a
//   * @param x
//   * @return
//   */
//  private def row(a: Array[Array[Array[Int]]], x: Int): Array[Array[Int]] = (for (i <- 0 until SIZE) yield a(x)(i)).toArray
//
//  /**
//   *
//   * @param a
//   * @param y
//   * @return
//   */
//  private def column(a: Array[Array[Array[Int]]], y: Int): Array[Array[Int]] = (for (i <- 0 until SIZE) yield a(i)(y)).toArray
//
//  private def miniGrid(a: Array[Array[Array[Int]]], x: Int, y: Int): Array[Array[Int]] =
//    (for (x1 <- (x / SIZE_SQRT * SIZE_SQRT) to x / SIZE_SQRT * SIZE_SQRT + 2;
//          y1 <- y / SIZE_SQRT * SIZE_SQRT to y / SIZE_SQRT * SIZE_SQRT + 2) yield a(x1)(y1)).toArray
//
//  private def cell(a: Array[Array[Int]], x1: Int, y1: Int, x2: Int, y2: Int): Array[Int] =
//    (for (x <- x1 to x2; y <- y1 to y2) yield a(x)(y)).toArray
//
//  /**
//   * Sudoku grid join with a solved sudoku grid
//   *
//   * @param a Sudoku grid Array[Int][Int]
//   * @param r Solved sudoku grid Array[Int][Int]
//   * @return Array[Int][Int]
//   */
//  private def join(a: Array[Array[Int]], r: Array[Array[Int]]): Array[Array[Int]] =
//    a.zip(r).map { case (x, y) => x.zip(y).map { case (i, j) => i + j } }
//
//  private def reduce(a: Array[Array[Array[Int]]]): Array[Array[Int]] = {
//    val array = Array.fill(SIZE, SIZE)(0)
//    for (x <- 0 until SIZE; y <- 0 until SIZE) {
//      if (a(x)(y).length == 1 && a(x)(y)(0) != 0)
//        array(x)(y) = a(x)(y)(0)
//      else if (a(x)(y).length > 1)
//        (1 to SIZE).foreach {
//          i => {
//            if (a(x)(y).contains(i) &&
//              (row(a, x).iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
//                column(a, y).iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
//                miniGrid(a, x, y).iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
//              array(x)(y) = i
//          }
//        }
//    }
//    array
//  }
//
//  /**
//   *
//   * @param a
//   * @param i
//   * @return
//   */
//  private def mappingNumber(a: Array[Array[Int]], i: Int): Array[Array[Int]] = {
//    val n = findNumber(a, 7)
//
//    var e = emptyCells(a)
//
//    for (m <- n)
//      e = e.filter(i => i(0) != m(0) && i(1) != m(1))
//    e
//  }
//
//  /**
//   *
//   * @param a
//   * @return
//   */
//  private def emptyCells(a: Array[Array[Int]]): Array[Array[Int]] =
//    (for (x <- 0 until SIZE; y <- 0 until SIZE; if a(x)(y) == 0) yield Array(x, y)).toArray
//
//  //private def cell(a: Array[Array[Int]], x:Int, y:Int): Array[Array[Int]] =
//  //    (for ( x1 <- (x/3 * 3) to x/3 * 3 + 2; y1 <- y/3 * 3 to y/3 *3 + 2) yield a(x1)(y1))
//
//  private def groupNumbers(a: Array[Array[Int]]): collection.mutable.Map[Int, ArrayBuffer[Array[Int]]] = {
//    val m = collection.mutable.Map[Int, ArrayBuffer[Array[Int]]]()
//
//    for (i <- 1 to SIZE) {
//      val ar = findNumber(a, i)
//      println(s"$i")
//      for (e <- ar) println(s"${e.view}")
//      //            println(s"($x,$y) => $v1")
//      //            if(!m.contains(i))
//      //                m(i) = ArrayBuffer(Array(x, y))
//      //            else
//      //                m(i) +:= Array(x, y)
//    }
//    m
//  }
//
//  /**
//   *
//   * @param a
//   * @param i
//   * @return
//   */
//  private def findNumber(a: Array[Array[Int]], i: Int): Array[Array[Int]] =
//    (for (x <- 0 until SIZE; y <- 0 until SIZE; if a(x)(y) == i) yield Array(x, y)).toArray
//
//  /**
//   *
//   * @param i
//   * @param pos
//   * @param a sudoku grid Array[ Array[ Int ] ]
//   * @return
//   */
//  @scala.annotation.tailrec
//  private def addNumberRandom(i: Int, pos: Array[Int], a: Array[Array[Int]]): Array[Array[Int]] = {
//    if (!empty(a)) {
//      if (availableNumbers(a, pos(0), pos(1)).length == 0) {
//        for (i <- 0 until SIZE; j <- 0 until SIZE) a(i)(j) = 0
//        pos(0) = 0
//        pos(1) = 0
//      }
//      val l = availableNumbers(a, pos(0), pos(1))
//      val n = l(scala.util.Random.nextInt(l.length))
//
//      if (!row(a, pos(0)).contains(i) && !column(a, pos(1)).contains(i) && !this.miniGrid(a, pos(0), pos(1)).contains(i)) {
//        a(pos(0))(pos(1)) = i
//        addNumberRandom(n, if (pos(0) < 8) Array(pos(0) + 1, pos(1)) else Array(0, pos(1) + 1), a)
//      } else
//        addNumberRandom(n, pos, a)
//    }
//    else
//      new Array[Array[Int]](0)
//  }
//
//
//  //    private def backTrack(a: Array[Array[Int]]): Boolean = {
//  //        show(a)
//  //        val l = possibleNumbersOrder(a).sortBy(_ (1).length)
//  //        if (l.length > 0) {
//  //            breakable {
//  //                for (e <- l; n <- e(1)) {
//  //                    val va = a(e(0)(0))(e(0)(1))
//  //                    a(e(0)(0))(e(0)(1)) = n
//  //                    if (backTrack(a) && empty(a)) {
//  //                        show(a)
//  //                        break
//  //                    }
//  //                    else {
//  //                        a(e(0)(0))(e(0)(1)) = va
//  //                        false
//  //                    }
//  //                }
//  //            }
//  //        }
//  //
//  //        true
//  //    }
//
//  //    private def backTrack(a: Array[Array[Int]]): Boolean = {
//  //        for (x <- 0 until size; y <- 0 until size) {
//  //            if (a(x)(y) == 0){
//  //                for (i <- availableNumbers(a, x, y)){
//  //                    a(x)(y) = i
//  //                    if(backTrack(a)) {
//  //                        return true
//  //                    }
//  //
//  //
//  //                }
//  //                a(x)(y) = 0
//  //                false
//  //            }else{
//  //                show(a)
//  //
//  //            }
//  //        }
//  //
//  //        true
//  //    }
//}
