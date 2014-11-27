package ar.edu.utn.tadp.argentina_express.objects_solution

trait DateCalculator {
  def isLastWeek : Boolean = { false }
  def isAfter20 : Boolean = { false }
}

object DateCalculator extends DateCalculator