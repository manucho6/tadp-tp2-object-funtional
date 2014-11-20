package ar.edu.utn.tadp.argentina_express.objects_solution

class Package (val shipment_type: ShipmentType, val size:Int){
  
  def cost : Double = {
    shipment_type.cost
  }
  
  def requiresCooling = { shipment_type == Cooling }
}