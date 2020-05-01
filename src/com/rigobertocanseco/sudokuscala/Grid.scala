package com.rigobertocanseco.sudokuscala

case class Grid(c: Array[Array[Int]]) {
  final val size: Int = 9
  final val size_sqrt: Int = (scala.math.sqrt(size)).toInt
  var cells: Array[Array[Cell]] = (for (row <- 0 until size; column <- 0 until size) yield
    if (c(row)(column) == 0)
      Cell(0, (1 to size).diff((
        (for (i <- 0 until size) yield c(row)(i)) ++
          (for (i <- 0 until size) yield c(i)(column)) ++
          (for (i <- (row / size_sqrt * size_sqrt) to row / size_sqrt * size_sqrt + 2;
                j <- column / size_sqrt * size_sqrt to column / size_sqrt * size_sqrt + 2) yield c(i)(j)))
        .distinct).toArray)
    else
      Cell(c(row)(column), Array())
    ).toArray.grouped(size).toArray

  (for (x <- 0 until size; y <- 0 until size) yield
    if (this.cells(x)(y).options.length == 0)
      Cell(this.cells(x)(y).value, Array())
    else
      (for(i <- 1 to size) yield
        if (this.cells(x)(y).options.contains(i) &&
          ((for (i <- 0 until size) yield this.cells(x)(i).options)
            .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
            (for (i <- 0 until size) yield this.cells(i)(y).options)
              .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
            (for (x1 <- (x / size_sqrt * size_sqrt) to x / size_sqrt * size_sqrt + 2;
                  y1 <- y / size_sqrt * size_sqrt to y / size_sqrt * size_sqrt + 2) yield this.cells(x1)(y1).options)
              .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
          Cell(i, Array())
        else Cell(0, Array())
        ).toArray.distinct.sortWith(_.value > _.value)(0)
    ).toArray.grouped(size).toArray



  val grid: Grid = reduce(this)

  cells = (for (i <- 0 until size; j <- 0 until size) yield grid.cells(i)(j)).toArray.grouped(size).toArray

  private def reduce(grid: Array[Array[Cell]]): Array[Array[Cell]] = {
    val nextGrid: Array[Array[Cell]] = grid.reduce()

    if(!nextGrid.equals(grid)) {
      reduce(nextGrid)
    } else {
      //val nextGrid: Grid = backTrack(nextGrid)
      nextGrid
    }
  }

  private def reduce(): Array[Array[Cell]] =
    (for (x <- 0 until size; y <- 0 until size) yield
      if (this.cells(x)(y).options.length == 0)
        Cell(this.cells(x)(y).value, Array())
      else
        (for(i <- 1 to size) yield
          if (this.cells(x)(y).options.contains(i) &&
            ((for (i <- 0 until size) yield this.cells(x)(i).options)
              .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
              (for (i <- 0 until size) yield this.cells(i)(y).options)
                .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
              (for (x1 <- (x / size_sqrt * size_sqrt) to x / size_sqrt * size_sqrt + 2;
                    y1 <- y / size_sqrt * size_sqrt to y / size_sqrt * size_sqrt + 2) yield this.cells(x1)(y1).options)
                .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
            Cell(i, Array())
          else Cell(0, Array())
          ).toArray.distinct.sortWith(_.value > _.value)(0)
      ).toArray.grouped(size).toArray
}
