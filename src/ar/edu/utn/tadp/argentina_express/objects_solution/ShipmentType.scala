package ar.edu.utn.tadp.argentina_express.objects_solution

abstract class ShipmentType(val cost: Double, val price: Double) 

case object Normal 	extends ShipmentType(10, 80)
case object Urgent 	extends ShipmentType(20, 110)
case object Fragile extends ShipmentType(18, 120)
case object Cooling extends ShipmentType(70, 210)