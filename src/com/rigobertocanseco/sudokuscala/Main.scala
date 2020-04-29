
package com.rigobertocanseco.sudokuscala

/**
 * 8 5 . |. . 2 |4 . .
 * 7 2 . |. . . |. . 9
 * . . 4 |. . . |. . .
 * ------+------+------
 * . . . |1 . 7 |. . 2
 * 3 . 5 |. . . |9 . .
 * . 4 . |. . . |. . .
 * ------+------+------
 * . . . |. 8 . |. 7 .
 * . 1 7 |. . . |. . .
 * . . . |. 3 6 |. 4 .
 * 8 5 9 |6 1 2 |4 3 7
 * 7 2 3 |8 5 4 |1 6 9
 * 1 6 4 |3 7 9 |5 2 8
 * ------+------+------
 * 9 8 6 |1 4 7 |3 5 2
 * 3 7 5 |2 6 8 |9 1 4
 * 2 4 1 |5 9 3 |7 8 6
 * ------+------+------
 * 4 3 2 |9 8 1 |6 7 5
 * 6 1 7 |4 2 5 |8 9 3
 * 5 9 8 |7 3 6 |2 4 1
 * (0.01 seconds)
 * . . 5 |3 . . |. . .
 * 8 . . |. . . |. 2 .
 * . 7 . |. 1 . |5 . .
 * ------+------+------
 * 4 . . |. . 5 |3 . .
 * . 1 . |. 7 . |. . 6
 * . . 3 |2 . . |. 8 .
 * ------+------+------
 * . 6 . |5 . . |. . 9
 * . . 4 |. . . |. 3 .
 * . . . |. . 9 |7 . .
 * 1 4 5 |3 2 7 |6 9 8
 * 8 3 9 |6 5 4 |1 2 7
 * 6 7 2 |9 1 8 |5 4 3
 * ------+------+------
 * 4 9 6 |1 8 5 |3 7 2
 * 2 1 8 |4 7 3 |9 5 6
 * 7 5 3 |2 9 6 |4 8 1
 * ------+------+------
 * 3 6 7 |5 4 2 |8 1 9
 * 9 8 4 |7 6 1 |2 3 5
 * 5 2 1 |8 3 9 |7 6 4
 */

object Main {
  def main(args: Array[String]): Unit = {
    //val a = sudoku.create()

    val a = Array(
      Array(8, 5, 0, 0, 0, 2, 4, 0, 0),
      Array(7, 2, 0, 0, 0, 0, 0, 0, 9),
      Array(0, 0, 4, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 1, 0, 7, 0, 0, 2),
      Array(3, 0, 5, 0, 0, 0, 9, 0, 0),
      Array(0, 4, 0, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 8, 0, 0, 7, 0),
      Array(0, 1, 7, 0, 0, 0, 0, 0, 0),
      Array(0, 0, 0, 0, 3, 6, 0, 4, 0))

    //println(a.iterator.flatMap(_.iterator).filter(_ == 0).iterator.nonEmpty)
    //.flatMap(_.iterator).filter(_ != 0)

    val sudoku: Sudoku = new Sudoku()
    sudoku.create(a)
    sudoku.getSolutions()
    sudoku.printGrid()

    val grid: Grid = new Grid(a)
    println(grid.toString())
    val grid2: Grid = grid.reduce()
    println(grid2.toString())

    //        val g1 = sudoku.resolver(a)
    //        sudoku.show(g1)
    //        (for ((v1, x) <- sudoku.allPossibleNumbers(g1).zipWithIndex; (v2, y) <- v1.zipWithIndex)
    //            yield Array(Array(x, y), v2)).filter(_(1).length > 1).sortBy(_(1).length)
    //            .foreach( v => println(v(0).view.mkString(",") + "=>" + v(1).view.mkString(",")))
    //
    //        sudoku.selectCell(g1, 0, 0)


  }
}
