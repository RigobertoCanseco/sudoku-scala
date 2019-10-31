package com.rigobertocanseco.sudokuscala

object Main {
    def main(args: Array[String]): Unit = {
        val sudoku:Sudoku = new Sudoku()
        //val a = sudoku.create()

        val a = Array(
            Array(5,3,0,0,7,0,0,0,0),
            Array(6,0,0,1,9,5,0,0,0),
            Array(0,9,8,0,0,0,0,6,0),
            Array(8,0,0,0,6,0,0,0,3),
            Array(4,0,0,8,0,3,0,0,1),
            Array(7,0,0,0,2,0,0,0,6),
            Array(0,6,0,0,0,0,2,8,0),
            Array(0,0,0,4,1,9,0,0,5),
            Array(0,0,0,0,8,0,0,7,9))

        sudoku.show(a)

        val solution = sudoku.resolver(a)
        sudoku.show(solution)
    }
}
