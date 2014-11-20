package ar.edu.utn.tadp.argentina_express.objects_solution

trait DistanceCalculator {
  def distanciaTerrestreEntre(office1: Office, office2: Office): Double = { 500 }
  def distanciaAereaEntre(office1: Office, office2: Office): Double =  { 100 }
  def cantidadPeajesEntre(office1: Office, office2: Office): Int = { 10 }
}

object DistanceCalculator extends DistanceCalculator