package ar.edu.utn.tadp.argentina_express.objects_solution

abstract class Transport(val capacity: Int, val cost_per_km: Int, val speed: Double) {

  var shipment_type: ShipmentType = _
  var destiny: Office = _
  var packages: Set[Package] = Set()
  var gps: Boolean = _
  var gps_video: Boolean = _
  var accommodation: Option[Accommodation] = _

  def canSend(destiny: Office, pack: Package): Boolean = { matchRequirements(pack) && matchDestiny(destiny) && hasSpace(pack) }

  def matchRequirements(pack: Package): Boolean = { pack.shipment_type == shipment_type }

  def matchDestiny(destiny_office: Office): Boolean = { destiny == null || destiny == destiny_office }

  def hasSpace(pack: Package): Boolean = { pack.size <= avaliableSpace }

  def avaliableSpace(): Int = { if (packages.isEmpty) capacity else capacity - packages.map(_.size).reduce(_ + _) }

  def register(destiny: Office, pack: Package) = {
    if (this.destiny == null) { this.destiny = destiny }
    packages += pack
  }
  
  def isPlane : Boolean = { false } 
  
  def  refrigerationCost : Double = { packages.filter(_.requiresCooling).map(_.cost).reduce(_ + _) }
 

  def travelCost(origin: Office, destiny: Office): Double
}

class Truck extends Transport(45, 100, 60) {
  def travelCost(origin: Office, destiny: Office): Double = {
    DistanceCalculator.distanciaTerrestreEntre(origin, destiny) * cost_per_km 
    + DistanceCalculator.cantidadPeajesEntre(origin, destiny) * 12 
    + refrigerationCost
  }
}
class Van extends Transport(9, 40, 80) {
  def travelCost(origin: Office, destiny: Office): Double = {
    DistanceCalculator.distanciaTerrestreEntre(origin, destiny) * cost_per_km
    + DistanceCalculator.cantidadPeajesEntre(origin, destiny) * 6 
    + refrigerationCost
  }
}

class Plain extends Transport(200, 500, 80) {
  override def isPlane : Boolean = { true }
  def travelCost(origin: Office, destiny: Office): Double = {
    if (origin.country == destiny.country) {
      DistanceCalculator.distanciaAereaEntre(origin, destiny) * cost_per_km
    } else {
      1.1 * DistanceCalculator.distanciaAereaEntre(origin, destiny) * cost_per_km
    }
  }
}

abstract class Accommodation

case object Dangerous extends Accommodation
case object Animals extends Accommodation
