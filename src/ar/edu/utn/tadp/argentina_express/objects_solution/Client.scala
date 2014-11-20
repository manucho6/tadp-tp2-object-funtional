package ar.edu.utn.tadp.argentina_express.objects_solution

class Client {

  def sendPackage(origin:Office, destiniy:Office, pack:Package ) : Boolean =  {  
    origin.sendPackage(destiniy, pack)
  } 
}