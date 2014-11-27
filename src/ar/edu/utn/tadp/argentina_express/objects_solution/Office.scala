package ar.edu.utn.tadp.argentina_express.objects_solution

class Office(val name: String, val country: String, var capacity: Int) {
  var tranports: Set[Transport] = Set()
  var incomming_shippings: Set[Shipping] = Set()
  var shippings: Set[Shipping] = Set()
  
}


case object Central extends Office("Central", "Argentina", 1000)