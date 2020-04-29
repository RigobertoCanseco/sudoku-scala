package com.rigobertocanseco.sudokuscala

class Grid(a: Array[Array[Int]]) {
  final val size: Int = 9
  final val size_sqrt: Int = (scala.math.sqrt(size)).toInt

  val cells: Array[Array[Cell]] =
    (for (row <- 0 until size; column <- 0 until size) yield
      if (a(row)(column) == 0)
        Cell(0, (1 to size).diff((
          (for (i <- 0 until size) yield a(row)(i)) ++
          (for (i <- 0 until size) yield a(i)(column)) ++
          (for (r <- (row / size_sqrt * size_sqrt) to row / size_sqrt * size_sqrt + 2;
                c <- column / size_sqrt * size_sqrt to column / size_sqrt * size_sqrt + 2) yield a(r)(c)))
          .distinct).toArray)
      else
        Cell(a(row)(column), Array())
      ).toArray.grouped(size).toArray

  def reduce(): Grid =
    new Grid(
      (for (x <- 0 until size; y <- 0 until size) yield
        if (cells(x)(y).options.length == 0)
          cells(x)(y).value
        else
          (for(i <- 1 to size) yield
            if (cells(x)(y).options.contains(i) &&
              ((for (i <- 0 until size) yield cells(x)(i).options)
                .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
                (for (i <- 0 until size) yield cells(i)(y).options)
                  .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1 ||
                (for (x1 <- (x / size_sqrt * size_sqrt) to x / size_sqrt * size_sqrt + 2;
                      y1 <- y / size_sqrt * size_sqrt to y / size_sqrt * size_sqrt + 2) yield cells(x1)(y1).options)
                  .toArray.iterator.flatMap(_.iterator).filter(_ == i).iterator.size == 1))
              i
            else 0
          ).toArray.distinct.sortWith(_ > _)(0)
      ).toArray.grouped(size).toArray)

  override def equals(obj: Any): Boolean = {
    val grid: Grid = obj.asInstanceOf[Grid]
    if ((for (row <- 0 until this.size; col <- 0 until this.size) yield
      if (this.cells(row)(col).value == grid.cells(row)(col).value) 1).length == this.size*this.size ) true
    else false
  }

  override def toString: String = {
    var string: String = " =========================================\n"
    for (i <- 0 until this.size) {
      for (j <- 0 until this.size) {
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
