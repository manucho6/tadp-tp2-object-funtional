package ar.edu.utn.tadp.argentina_express.objects_solution

class Transport(val transport_type: TransportType) {

  var shipment_type: ShipmentType = _
  var office: Office = _
  var destiny: Office = _
  var packages: Set[Package] = Set()
  var gps: Boolean = _
  var gps_video: Boolean = _
  var accommodation: Option[Accommodation] = _

  def travelCost: Double = {
    (packagesCost + distanceCost + acomodationCost) * (aditionalCost)
  }

  def avaliableSpace: Double = { packages.foldLeft(0.0)(_ + _.size) }

  def avaliableSpacePercentage: Double = { avaliableSpace / transport_type.capacity }

  def distanceCost: Double = {
    transport_type match {
      case Truck => 12 * DistanceCalculator.cantidadPeajesEntre(office, destiny) + transport_type.cost_per_km * distance
      case Van => 6 * DistanceCalculator.cantidadPeajesEntre(office, destiny) + transport_type.cost_per_km * distance
      case Plain => transport_type.cost_per_km * distance
    }
  }

  def packagesCost: Double = {
    transport_type match {
      case Truck | Van => packages.map(p => if (p.requiresCooling) p.cost + 5 else p.cost).sum
      case Plain => packages.foldLeft(0.0)(_ + _.cost)
    }
  }

  def aditionalCost: Double = {
    transport_type match {
      case Truck => freeSpaceCost + featuresExtraCost + centralTravelCost
      case Van => freeSpaceCost + featuresExtraCost
      case Plain => freeSpaceCost + featuresExtraCost + centralTravelCost + InternationalFeeCost
    }
  }

  def freeSpaceCost: Double = {
    if (paysFreeSpaceCost)
      transport_type match {
        case Truck => 1 + avaliableSpacePercentage
        case Van => 2
        case Plain => 3
      }
    else
      return 0
  }

  def paysFreeSpaceCost: Boolean = {
    if (avaliableSpacePercentage < 0.2)
      transport_type match {
        case Truck => urgenPackages.size < 3
        case Van => office == Central || destiny == Central
        case Plain => true
      }
    else
      return false
  }

  def centralTravelCost: Double = {
    if (destiny == Central)
      transport_type match {
        case Truck => if (DateCalculator.isLastWeek) 0.02 else 0
        case Plain => if (DateCalculator.isAfter20) -0.2 else 0
      }
    else
      0
  }

  def InternationalFeeCost: Double = { if (office.country != destiny.country) 0.1 else 0 }

  def featuresExtraCost: Double = { 2 * distance * featuresCost }

  def featuresCost: Double = {
    var total: Double = 0

    if (gps) total += 0.5
    if (gps_video) total += 3.74

    return total
  }

  def acomodationCost: Double = {
    dangerousCost + animalCost
  }

  def dangerousCost: Double = {
    transport_type match {
      case Truck => 600 + urgentPackagesCost
      case other => 600
    }
  }

  def urgentPackagesCost: Double = {
    3 * (urgenPackages.foldLeft(0.0)(_ + _.size) / transport_type.capacity)
  }

  def urgenPackages: Set[Package] = { packages.filter(_.shipment_type == Urgent) }

  def animalCost: Double = {
    distance match {
      case it if 0 until 100 contains it => 50
      case it if 101 until 200 contains it => 87
      case _ => 137
    }
  }

  def distance: Double = {
    transport_type match {
      case Truck | Van => DistanceCalculator.distanciaTerrestreEntre(office, destiny)
      case Plain => DistanceCalculator.distanciaAereaEntre(office, destiny)
    }
  }
}

abstract class TransportType(val capacity: Int, val cost_per_km: Int, val speed: Double)

case object Truck extends TransportType(45, 100, 60)
case object Van extends TransportType(9, 40, 80)
case object Plain extends TransportType(200, 500, 80)

abstract class Accommodation

case object Dangerous extends Accommodation
case object Animals extends Accommodation
