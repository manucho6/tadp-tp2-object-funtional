package ar.edu.utn.tadp.argentina_express.objects_solution

class Office(val name: String, val country: String, var capacity: Int) {
  var tranports: Set[Transport] = Set()
  var incomming_shippings: Set[Shipping] = Set()
  var shippings: Set[Shipping] = Set()
  
  def canSend(pack:Package, destiny:Office) : Boolean = { tranports.exists(t => t.canSend(destiny, pack)) }
  def canRecive(pack:Package) : Boolean = { pack.size <= avaliableSize() }
  
  def avaliableSize() : Int = { capacity - calculateSize(shippings) - calculateSize(incomming_shippings) }
  
  def calculateSize(shippings: Set[Shipping] ) : Int =  shippings.map(_.pack.size).reduce(_+_)
   
  def sendPackage(destiny:Office, pack:Package) : Boolean = {
    if (canSend(pack, destiny) &&  destiny.canRecive(pack)) {
      createShipping(destiny, pack)
      return true
    }
    return false
  } 
  
  def createShipping(destiny:Office, pack:Package) : Shipping = {
    var transport : Option[Transport] = tranports.find (t => t.canSend(destiny, pack))
    if (transport.isDefined) {
      transport.get.register(destiny, pack) 
      return new Shipping(this, destiny, pack, transport.get)
     }
    throw new MatchError("Transport not found");
    null
  }  
}


case object Central extends Office("Central", "Argentina", 1000)