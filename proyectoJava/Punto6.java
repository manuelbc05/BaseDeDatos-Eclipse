package proyectoCapituloDos;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
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
			System.out.println("- - - Menú de creación de base de datos - - -");
			System.out.println("1 -> Crear base de datos");
			System.out.println("2 -> Oracle SQL");
			System.out.println("3 -> Borrar ambas SQL");
			System.out.println("4 -> Menú de conexiones JDBC");
			System.out.println("5 -> Datos");
			System.out.println("6 -> Punto Seis");
			System.out.println("7 -> Mostrar metadatos SQL");
			System.out.println("8 -> Salir");
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
				break;
			case 6:
				puntoSeis();
				break;
			case 7:
				break;
			case 8:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción inválida. Intenta de nuevo.");
			}

			System.out.println();
		} while (opcion != 8);

		sc.close();

	}

	// Punto 3
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

	// Punto 4
	public static void menuConexiones() {
		int op;
		do {
			System.out.println("- - - CONEXIÓN CON DISTINTAS BASES DE DATOS - - -");
			System.out.println("- - GESTION TIENDA INFORMATICA - -");
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

			System.out.println("- - - Contenido tabla DEPARTAMENTOS - - -");
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

	// Punto 5
	public static void datos() {
		int opciones;
		do {
			System.out.println("- - - OPERACIONES SOBRE DATOS - - -");
			System.out.println("1. Mostrar listado de artículos ");
			System.out.println("2. Mostrar listado de clientes");
			System.out.println("3. Mostrar pedidos (cabecera) ");
			System.out.println("4. Mostrar líneas de un pedido");
			System.out.println("5. Insertar un nuevo artículo ");
			System.out.println("6. Modificar el precio de un artículo ");
			System.out.println("7. Eliminar un artículo (si no está en pedidos) ");
			System.out.println("0. Volver ");
			System.out.println("- - - - - - - - - - - - - - - - - - - -");
			System.out.print("Elige una opcion: ");
			opciones = sc.nextInt();
			sc.nextLine();

			switch (opciones) {
			case 1:
				mostrarArticulos();
				break;
			case 2:
				mostrarClientes();
				break;
			case 3:
				mostrarPedidosCabecera();
				break;
			case 4:
				mostrarLineasPedido();
				break;
			case 5:
				insertarArticulo();
				break;
			case 6:
				modificarPrecioArticulo();
				break;
			case 7:
				eliminarArticulo();
				break;
			case 0:
				break;
			default:
				System.out.println("Opcion no valida, prueba otra vez");
			}
		} while (opciones != 0);
	}
	
	// Punto 6
	public static void puntoSeis() {
		int opciones;
		do {
			System.out.println("- - - Punto número 6 - - -");
			System.out.println("1. Mostrar listado de artículos ");
			System.out.println("2. Inserción de un nuevo cliente ");
			System.out.println("3. Actualización del salario de un empleado dado su identificador ");
			System.out.println("4. Consulta de reparaciones filtradas por estado o rango de fechas ");
			System.out.println("0. Volver ");
			System.out.println("- - - - - - - - - - - - - - - - - - - -");
			System.out.print("Elige una opcion: ");
			opciones = sc.nextInt();
			sc.nextLine();

			switch (opciones) {
			case 1:
				articulosPorCategoria();
				break;
			case 2:
				insertarNuevoCliente();
				break;
			case 3:
				modificarSalarioEmpleado();
				break;
			case 4:
				reparacionesPorEstado();
				break;
			case 0:
				break;
			default:
				System.out.println("Opcion no valida, prueba otra vez");
			}
		} while (opciones != 0);
	}

	private static Connection obtenerConexionMySQL() throws SQLException, ClassNotFoundException {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost/ejemplo", "root", "");
	}

	private static void mostrarArticulos() {
		String sql = "SELECT id_articulo, nombre, categoria, precio, stock FROM articulos";
		try (Connection con = obtenerConexionMySQL();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			System.out.println("- - - LISTADO ARTÍCULOS - - -");
			while (rs.next()) {
				System.out.printf("%d | %s | %s | %.2f € | stock: %d%n", rs.getInt("id_articulo"),
						rs.getString("nombre"), rs.getString("categoria"), rs.getDouble("precio"), rs.getInt("stock"));
			}
		} catch (Exception e) {
			System.out.println("Error mostrando artículos");
			e.printStackTrace();
		}
	}

	private static void mostrarClientes() {
		String sql = "SELECT id_cliente, nombre_completo, nif_cif, telefono, correo, ciudad FROM clientes";
		try (Connection con = obtenerConexionMySQL();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			System.out.println("- - - LISTADO CLIENTES - - -");
			while (rs.next()) {
				System.out.printf("%d | %s | %s | %s | %s | %s%n", rs.getInt("id_cliente"), rs.getString("nombre_completo"),
						rs.getString("nif_cif"), rs.getString("telefono"), rs.getString("correo"), rs.getString("ciudad"));
			}
		} catch (Exception e) {
			System.out.println("Error mostrando clientes");
			e.printStackTrace();
		}
	}

	private static void mostrarPedidosCabecera() {
		String sql = "SELECT id_pedido, id_cliente, id_empleado, id_tienda, fecha, estado FROM pedidos";
		try (Connection con = obtenerConexionMySQL();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			System.out.println("- - - CABECERAS DE PEDIDOS - - -");
			while (rs.next()) {
				System.out.printf("%d | cliente:%d | vendedor:%d | tienda:%d | fecha:%s | estado:%s%n",
						rs.getInt("id_pedido"), rs.getInt("id_cliente"), rs.getInt("id_empleado"),
						rs.getInt("id_tienda"), rs.getDate("fecha"), rs.getString("estado"));
			}
		} catch (Exception e) {
			System.out.println("Error mostrando pedidos");
			e.printStackTrace();
		}
	}

	private static void mostrarLineasPedido() {
		System.out.print("Introduce id del pedido para ver sus líneas: ");
		int idPedido = sc.nextInt();
		String sql = "SELECT id_linea, id_pedido, id_articulo, cantidad, precio_unitario FROM lineaspedido WHERE id_pedido = ?";
		try (Connection con = obtenerConexionMySQL(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idPedido);
			try (ResultSet rs = ps.executeQuery()) {
				System.out.println("- - - LÍNEAS DEL PEDIDO " + idPedido + " - - -");
				while (rs.next()) {
					System.out.printf("%d | articulo:%d | cantidad:%d | precio:%.2f €%n", rs.getInt("id_linea"),
							rs.getInt("id_pedido"), rs.getInt("id_articulo"), rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
				}
			}
		} catch (Exception e) {
			System.out.println("Error mostrando líneas de pedido");
			e.printStackTrace();
		}
	}

	private static void insertarArticulo() {
		System.out.println("- - Insertar nuevo artículo - -");
		System.out.print("Id Articulo (Único): ");
		int ID = Integer.parseInt(sc.nextLine());
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Categoría: ");
		String categoria = sc.nextLine();
		System.out.print("Precio (ej. 199.99): ");
		double precio = Double.parseDouble(sc.nextLine());
		System.out.print("Stock (entero): ");
		int stock = Integer.parseInt(sc.nextLine());
		System.out.print("Proveedor id (opcional, 0 si ninguno): ");
		int proveedorId = Integer.parseInt(sc.nextLine());

		String sql = "INSERT INTO articulos (id_articulo, nombre, categoria, precio, stock, id_proveedor) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = obtenerConexionMySQL();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, ID);
			ps.setString(2, nombre);
			ps.setString(3, categoria);
			ps.setDouble(4, precio);
			ps.setInt(5, stock);
			if (proveedorId == 0)
				ps.setNull(6, java.sql.Types.INTEGER);
			else
				ps.setInt(6, proveedorId);

			int filas = ps.executeUpdate();
			System.out.println("Filas insertadas: " + filas);
			try (ResultSet keys = ps.getGeneratedKeys()) {
				if (keys.next()) {
					System.out.println("ID artículo creado: " + keys.getInt(1));
				}
			}
		} catch (Exception e) {
			System.out.println("Error insertando artículo");
			e.printStackTrace();
		}
	}

	private static void modificarPrecioArticulo() {
		System.out.print("Introduce id del artículo a modificar: ");
		int id = sc.nextInt();
		System.out.print("Introduce nuevo precio: ");
		double nuevoPrecio = sc.nextDouble();
		sc.nextLine();

		String sql = "UPDATE articulos SET precio = ? WHERE id_articulo = ?";
		try (Connection con = obtenerConexionMySQL(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, nuevoPrecio);
			ps.setInt(2, id);
			int filas = ps.executeUpdate();
			System.out.println("Filas afectadas: " + filas);
		} catch (Exception e) {
			System.out.println("Error modificando precio");
			e.printStackTrace();
		}
	}

	private static void eliminarArticulo() {
		System.out.print("Introduce id del artículo a eliminar: ");
		int id = sc.nextInt();
		sc.nextLine();

		String checkSql = "SELECT COUNT(*) AS cnt FROM lineaspedido WHERE id_articulo = ?";
		String deleteSql = "DELETE FROM articulos WHERE id_articulo = ?";

		try (Connection con = obtenerConexionMySQL(); PreparedStatement psCheck = con.prepareStatement(checkSql)) {
			psCheck.setInt(1, id);
			try (ResultSet rs = psCheck.executeQuery()) {
				if (rs.next() && rs.getInt("cnt") > 0) {
					System.out.println("No se puede eliminar: el artículo está presente en líneas de pedido.");
					return;
				}
			}

			try (PreparedStatement psDel = con.prepareStatement(deleteSql)) {
				psDel.setInt(1, id);
				int filas = psDel.executeUpdate();
				System.out.println("Filas eliminadas: " + filas);
			}
		} catch (Exception e) {
			System.out.println("Error eliminando artículo");
			e.printStackTrace();
		}
	}
	
	private static void articulosPorCategoria() {
		System.out.print("Introduce la cateogría del articulo que quieres ver: ");
		String categoria = sc.nextLine();
		String sql = "SELECT id_articulo, nombre, categoria, precio, stock, id_proveedor FROM articulos WHERE categoria = ?";
		try (Connection con = obtenerConexionMySQL(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, categoria);
			try (ResultSet rs = ps.executeQuery()) {
				System.out.println("- - - Categoria " + categoria + " - - -");
				while (rs.next()) {
				    System.out.printf(
				        "%d | articulo:%s | categoria:%s | precio:%.2f € | stock:%d | proveedor:%d%n",
				        rs.getInt("id_articulo"),
				        rs.getString("nombre"),
				        rs.getString("categoria"),
				        rs.getDouble("precio"),
				        rs.getInt("stock"),
				        rs.getInt("id_proveedor")
				    );
				}
			}
		} catch (Exception e) {
			System.out.println("Error los articulos por categoria mostro");
			e.printStackTrace();
		}
	}
	
	private static void insertarNuevoCliente() {
		System.out.println("- - Insertar nuevo artículo - -");
		System.out.print("Id del cliente (Único): ");
		int id_cliente = Integer.parseInt(sc.nextLine());
		System.out.print("Nombre completo: ");
		String nombre_completo = sc.nextLine();
		System.out.print("NIF o CIF: ");
		String nif_cif = sc.nextLine();
		System.out.print("Telefono: ");
		int telefono = Integer.parseInt(sc.nextLine());
		System.out.print("Correo: ");
		String correo = sc.nextLine();
		System.out.print("Ciudad: ");
		String ciudad = sc.nextLine();

		String sql = "INSERT INTO clientes (id_cliente, nombre_completo, nif_cif, telefono, correo, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = obtenerConexionMySQL();
				PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
			ps.setInt(1, id_cliente);
			ps.setString(2, nombre_completo);
			ps.setString(3, nif_cif);
			ps.setInt(4, telefono);
			ps.setString(5, correo);
			ps.setString(6, ciudad);

			int filas = ps.executeUpdate();
			System.out.println("Filas insertadas: " + filas);
			try (ResultSet keys = ps.getGeneratedKeys()) {
				if (keys.next()) {
					System.out.println("ID artículo creado: " + keys.getInt(1));
				}
			}
		} catch (Exception e) {
			System.out.println("Error insertando el cliente");
			e.printStackTrace();
		}
	}
	
	private static void modificarSalarioEmpleado() {
		System.out.print("Introduce id del artículo a modificar: ");
		int id = sc.nextInt();
		System.out.print("Introduce nuevo salario: ");
		double nuevoSalario = sc.nextDouble();
		sc.nextLine();

		String sql = "UPDATE empleados SET salario = ? WHERE id_empleado = ?";
		try (Connection con = obtenerConexionMySQL(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, nuevoSalario);
			ps.setInt(2, id);
			int filas = ps.executeUpdate();
			System.out.println("Filas afectadas: " + filas);
		} catch (Exception e) {
			System.out.println("Error modificando el salario");
			e.printStackTrace();
		}
	}
	
	private static void reparacionesPorEstado() {
		System.out.print("Introduce el estado por el que quieres buscar (En proceso, Pendiente o Finalizada): ");
		String estado = sc.nextLine();
		String sql = "SELECT id_reparacion, id_cliente, id_empleado, id_tienda, fecha_entrada, fecha_salida, descripcion, diagnostico, coste_mano_obra, estado FROM reparaciones WHERE estado = ?";
		try (Connection con = obtenerConexionMySQL(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, estado);
			try (ResultSet rs = ps.executeQuery()) {
				System.out.println("- - - Estado " + estado + " - - -");
				while (rs.next()) {
					System.out.printf(
						    "%d | cliente:%d | empleado:%d | tienda:%d | entrada:%s | salida:%s | desc:%s | diag:%s | coste:%.2f € | estado:%s%n",
						    rs.getInt("id_reparacion"),
						    rs.getInt("id_cliente"),
						    rs.getInt("id_empleado"),
						    rs.getInt("id_tienda"),
						    rs.getDate("fecha_entrada"),
						    rs.getDate("fecha_salida"),
						    rs.getString("descripcion"),
						    rs.getString("diagnostico"),
						    rs.getDouble("coste_mano_obra"),
						    rs.getString("estado")
						);
				}
			}
		} catch (Exception e) {
			System.out.println("Error al buscar por estado");
			e.printStackTrace();
		}
	}
}
