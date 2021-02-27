package pe.com.capacitacion.controller;
 
import org.springframework.http.HttpStatus; 
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.extern.slf4j.Slf4j; 
import java.util.ArrayList;
import java.util.List;
 
/**
 * ClienteController
 * @author cguerra
 **/
 @Slf4j      //Autogenerar LOG4J. 
 @RestController
 @RequestMapping( "/dummy-micro-cliente" )   //NO USAR: [server.servlet.context-path], 'BOOT-ADMIN' reconocera el 'ACTUATOR'.
 public class ClienteController{
 
        private String vClientes_01 = "{ \"nombre\": \"PAOLO GUERRERO\", \"edad\": 35, \"rol\": \"CONSULTOR\",   \"direccion\": \"Calle. 123 Chorrillos\", \"dni\": \"41816133\", \"version\": \"v1\" }";
        private String vClientes_02 = "{ \"nombre\": \"LUIS GUADALUPE\", \"edad\": 40, \"rol\": \"PROGRAMADOR\", \"direccion\": \"Av. 333 Lince\",         \"dni\": \"45886854\", \"version\": \"v1\" }"; 	 
        private String vClientes_03 = "{ \"nombre\": \"PEDRO SALAZAR\",  \"edad\": 30, \"rol\": \"ARQUITECTO\",  \"direccion\": \"Jiron. 123 Lima\",       \"dni\": \"41818956\", \"version\": \"v1\" }";
        private String vClientes_04 = "[" + vClientes_01 + "," + vClientes_02 + "," + vClientes_03 + "]";	 
	 
        private List<String> listaClientes = new ArrayList<String>();  
        
	   /** 
	    * consultarClientesPorId	
	    * @param  id
	    * @return String 
	    **/
		@GetMapping( "/get/clientes/{id}" )
		public ResponseEntity<String> consultarClientesPorId( @PathVariable( "id" ) long id ){
			   log.info( "'consultarClientesPorId': id={}", id );
  
			   String objResponseMsg = "";
			   
			   try {
				   this.listaClientes.add( this.vClientes_01 );	   
				   this.listaClientes.add( this.vClientes_02 );
				   this.listaClientes.add( this.vClientes_03 );
				   
				   String vDatoJson = "";
				   for( int i=0; i<this.listaClientes.size(); i++ ){	        	   
					   if( (i+1) == id ){
						   vDatoJson = this.listaClientes.get( i ); 
						   break; 
					   }  
				   }
				   
				   objResponseMsg = vDatoJson;
				   
				   //return ResponseEntity.status( HttpStatus.OK ).body( objResponseMsg ); 
				   
				   Thread.sleep( 1000 * 5 ); //SOLO PARA PRUEBAS
				   return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( objResponseMsg ); //SOLO PARA PRUEBAS
			   }
			   catch( Exception e ) { 
				      return ResponseEntity.status( HttpStatus.INTERNAL_SERVER_ERROR ).body( objResponseMsg );
			   }			   
		} 
		
	   /**
	    * consultarClientes	
	    * @return String 
	    **/
		@GetMapping( "/get/clientes" )
		public String  consultarClientes(){
			   log.info( "'consultarClientes'" );
 
			   String objResponseMsg = vClientes_04;
			   return objResponseMsg; 
		} 
		
 }

 