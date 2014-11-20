package ar.edu.utn.tadp.argentina_express.objects_solution

import org.junit.Assert._
import org.junit.Test
import org.junit.Before
import org.junit.runner.RunWith
import ar.edu.utn.tadp.argentina_express.objects_solution
import com.sun.org.apache.xalan.internal.xsltc.compiler.Include

class ArgentinaExpressTest {

  var cliente: Client = _
  var oficina_origen: Office = _
  var oficina_destino: Office = _
  var paquete: Package = _
  var paquete_gigante: Package = _
  var truck : Truck = new Truck

  @Before
  def initialize() {
    cliente = new Client
    truck.shipment_type = Normal
    oficina_origen = new Office("Origen","A", 1000)
    oficina_origen.tranports += truck
    oficina_destino = new Office("Destino","B", 1000)
    paquete = new Package(Normal, 10)
    paquete_gigante = new Package(Normal, 1000)
  }

  @Test
  def `Realizar envio` = {
    assertTrue(cliente.sendPackage(oficina_origen, oficina_destino, paquete))
    assertFalse(cliente.sendPackage(oficina_origen, oficina_destino, paquete_gigante))
  }
  
  @Test
  def `Calcular costo de envio` = {
    assertEquals(oficina_origen.shippings.head.cost, 1000)
  }
}