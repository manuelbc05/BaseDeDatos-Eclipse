package proyectoCapituloDos;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.SQLException;
import java.util.Scanner;

import org.apache.ibatis.jdbc.ScriptRunner;

public class main {

	static Scanner sc = new Scanner(System.in);

	public static void main(String[] args) {
		int opcion;

		do {
			System.out.println("=== Menú de creación de base de datos ===");
			System.out.println("1 -> Crear base de datos");
			System.out.println("2 -> Oracle SQL");
			System.out.println("3 -> Borrar ambas SQL");
			System.out.println("4 -> Menú de conexiones JDBC");
			System.out.println("5. Datos");
			System.out.println("6 -> Salir");
			System.out.print("Selecciona una opción: ");
			opcion = sc.nextInt();

			switch (opcion) {
			case 1:
				instalarMySQL();
				break;
			case 2:
				instalarOracle();
				break;
			case 3:
				eliminarAmbasSQL();
				break;
			case 4:
				menuConexiones();
				break;
			case 5:
				datos();
			case 6:
				System.out.println("Saliendo del programa...");
				break;
			default:
				System.out.println("Opción inválida. Intenta de nuevo.");
			}

			System.out.println();
		} while (opcion != 5);

		sc.close();

	}
	
	
	//Punto 3
	public static void instalarMySQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo?allowMultiQueries=true",
					"root", "");
			Reader reader = new FileReader("./script/accesoDatosmysql.sql");

			// Crear un objeto ScriptRunner
			ScriptRunner scriptRunner = new ScriptRunner(conexion);
			scriptRunner.setLogWriter(null); // Para que no muestre el log
			scriptRunner.setAutoCommit(false); // Usar o no autocommit
			scriptRunner.runScript(reader); // ejecutar el script
			conexion.commit(); // confirmar los cambios
			System.out.println("Script ejecutado con éxito (MySQL)");
			conexion.close();
		} catch (ClassNotFoundException | IOException | java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public static void instalarOracle() {
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection conexion = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "EJEMPLO",
					"ejemplo");
			Reader reader = new FileReader("./script/accesoDatosoracle.sql");

			// Crear un objeto ScriptRunner
			ScriptRunner scriptRunner = new ScriptRunner(conexion);
			scriptRunner.setLogWriter(null);
			scriptRunner.setAutoCommit(false);
			scriptRunner.runScript(reader);
			conexion.commit();

			System.out.println("Script ejecutado con éxito (Oracle)");
			conexion.close();
		} catch (ClassNotFoundException | IOException | java.sql.SQLException e) {
			e.printStackTrace();
		}
	}

	public static void eliminarAmbasSQL() {
		try {
			Class.forName("com.mysql.cj.jdbc.Driver");
			Class.forName("oracle.jdbc.driver.OracleDriver");

			Connection conexion = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo?allowMultiQueries=true",
					"root", "");
			Connection conexion2 = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "EJEMPLO",
					"ejemplo");

			Reader reader = new FileReader("./script/drop_all_mysql.sql");
			Reader reader2 = new FileReader("./script/drop_all_oracle.sql");

			ScriptRunner scriptRunner = new ScriptRunner(conexion);
			ScriptRunner scriptRunner2 = new ScriptRunner(conexion2);

			scriptRunner.setLogWriter(null);
			scriptRunner.setAutoCommit(false);
			scriptRunner.runScript(reader);

			scriptRunner2.setLogWriter(null);
			scriptRunner2.setAutoCommit(false);
			scriptRunner2.runScript(reader2);

			conexion.commit();
			conexion2.commit();

			System.out.println("Ambas SQL borradas correctamente");

			conexion.close();
			conexion2.close();
		} catch (ClassNotFoundException | IOException | java.sql.SQLException e) {
			e.printStackTrace();
		}
	}
	
	
	//Punto 4
	public static void menuConexiones() {
		int op;
		do {
			System.out.println("===CONEXIÓN CON DISTINTAS BASES DE DATOS===");
			System.out.println("__GESTION TIENDA INFORMATICA__");
			System.out.println("1. Conectar a MySQL");
			System.out.println("2. Conectar a Oracle");
			System.out.println("0. Salir");
			System.out.print("Introduce una opcion: ");
			op = sc.nextInt();

			switch (op) {
			case 1:
				probarConexion("mysql");
				break;
			case 2:
				probarConexion("oracle");
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida.");
			}

		} while (op != 0);
	}

	public static void probarConexion(String tipo) {
		try {
			Connection con = null;

			// Seleccionamos el tipo de conexión según opción
			switch (tipo) {
			case "mysql":
				Class.forName("com.mysql.cj.jdbc.Driver"); // cargar driver MySQL
				con = DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "root", "");
				break;

			case "oracle":
				Class.forName("oracle.jdbc.driver.OracleDriver"); // cargar driver Oracle
				con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521/XEPDB1", "EJEMPLO", "ejemplo");
				break;
			}

			// Confirmamos que estamos conectados
			System.out.println("\nConexión establecida con " + tipo.toUpperCase() + "\n");

			// Ejecutar consulta departamentos
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("SELECT * FROM departamentos");

			System.out.println("=== Contenido tabla DEPARTAMENTOS ===");
			while (rs.next()) {
				System.out.printf("%d - %s - %s%n", rs.getInt(1), rs.getString(2), rs.getString(3));
			}

			rs.close();
			st.close();
			con.close(); // cerrar conexión

		} catch (Exception e) {
			System.out.println("Error al conectar con " + tipo);
			e.printStackTrace();
		}
	}
	
	//Punto 5
	public static void datos() {
		int opciones;
		do {
			System.out.println("===OPERACIONES SOBRE DATOS===");
			System.out.println("1. Mostrar listado de artículos ");
			System.out.println("2. Mostrar listado de clientes" );
			System.out.println("3. Mostrar pedidos (cabecera) ");
			System.out.println("4. Mostrar líneas de un pedido");
			System.out.println("5. Insertar un nuevo artículo ");
			System.out.println("6. Modificar el precio de un artículo ");
			System.out.println("7. Eliminar un artículo (si no está en pedidos) ");
			System.out.println("0. Volver ");
			System.out.println("===============================");
			System.out.println("Elige una opcion ");
			opciones=sc.nextInt();
			
			switch(opciones) {
				case 1:
					break;
				case 2:
					break;
				case 3:
					break;
				case 4:
					break;
				case 5:
					break;
				case 6:
					break;
				case 7:
					break;
				case 0:
					break;
				default:
					System.out.println("Opcion no valida, prueba otra vez");
			}
		}while(opciones != 0);
		
	}
} 