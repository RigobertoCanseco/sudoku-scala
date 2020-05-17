package com.rigobertocanseco.sudokuscala

case class Grid(cells: Array[Array[Cell]]) {

  def this(array: Array[Array[Int]]) {
    this((for (row <- 0 until 9; col <- 0 until 9) yield
      if (array(row)(col) == 0) Cell(0,
        (1 to 9).diff(((for (i <- 0 until 9) yield array(row)(i)) ++
          (for (i <- 0 until 9) yield array(i)(col)) ++
          (for (i <- row / 3 * 3 to row / 3 * 3 + 2; j <- col / 3 * 3 to col / 3 * 3 + 2) yield array(i)(j)))
        .distinct).toArray)
      else Cell(array(row)(col), Array())
      ).toArray.grouped(9).toArray)
  }

  val reduce: Array[Array[Cell]] = (for (x <- 0 until 9; y <- 0 until 9) yield
    if (this.cells(x)(y).options.length == 0)
      Cell(this.cells(x)(y).value, Array())
    else
      (for(i <- 1 to 9) yield
        if (this.cells(x)(y).options.contains(i) &&
          ((for (i <- 0 until 9) yield this.cells(x)(i).options)
            .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
            (for (i <- 0 until 9) yield this.cells(i)(y).options)
              .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
            (for (x1 <- (x / 3 * 3) to x / 3 * 3 + 2;
                  y1 <- y / 3 * 3 to y / 3 * 3 + 2) yield this.cells(x1)(y1).options)
              .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
          Cell(i, Array())
        else Cell(0, Array())
        ).toArray.distinct.sortWith(_.value > _.value)(0)
    ).toArray.grouped(9).toArray

  def reduce(cells: Array[Array[Cell]]): Array[Array[Cell]] = {
    val grid: Grid = Grid(cells)
    val reduceCells: Array[Array[Cell]] = grid.reduce
    val nextGrid: Grid = Grid(reduceCells)

    if (!nextGrid.equals(grid)) reduce(reduceCells)
    else reduceCells
  }

  def solver: Array[Array[Cell]] = reduce(cells)

  override def equals(obj: Any): Boolean = {
    val grid: Grid = obj.asInstanceOf[Grid]
    if ( (for (row <- 0 until 9; col <- 0 until 9) yield
      if (this.cells(row)(col).value == grid.cells(row)(col).value) 1 else 0).count(_==1) == 81 ) true
    else false
  }

  override def toString: String = {
    var string: String = " =========================================\n"
    for (i <- 0 until 9) {
      for (j <- 0 until 9) {
        if (j % 3 == 0)
          string += s" || ${if (this.cells(i)(j).value == 0) "*" else this.cells(i)(j).value}"
        else string += s" | ${if (this.cells(i)(j).value == 0) "*" else this.cells(i)(j).value}"
      }
      string += " ||\n"
      if ((i + 1) % 3 == 0)
        string += " =========================================\n"
    }
    string
  }
}
