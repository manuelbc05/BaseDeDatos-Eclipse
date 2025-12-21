package proyectoCapituloDos;

import java.io.FileReader;

public class main {

	static Scanner sc = new Scanner(System.in);

	enum TipoBD {
		MYSQL, ORACLE
	}

	static TipoBD bdSeleccionada;

	static final String MYSQL_URL = "jdbc:mysql://localhost:3306/ejemplo?allowMultiQueries=true&useSSL=false&serverTimezone=UTC";
	static final String MYSQL_USER = "root";
	static final String MYSQL_PASS = "";

	static final String ORACLE_URL = "jdbc:oracle:thin:@localhost:1521/XEPDB1";
	static final String ORACLE_USER = "EJEMPLO";
	static final String ORACLE_PASS = "ejemplo";

	public static void main(String[] args) {
		seleccionarBaseDeDatos();

		int opcion;
		do {
			System.out.println("\n--- MENÚ PRINCIPAL (" + bdSeleccionada + ") ---");
			System.out.println("1 -> Crear base de datos");
			System.out.println("2 -> Borrar base de datos");
			System.out.println("3 -> Menú JDBC");
			System.out.println("4 -> Datos");
			System.out.println("5 -> Punto Seis");
			System.out.println("6 -> Metadatos");
			System.out.println("7 -> Procedimientos / Funciones");
			System.out.println("8 -> Informes");
			System.out.println("0 -> Salir");
			System.out.print("Opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				instalarBD();
				break;
			case 2:
				eliminarBD();
				break;
			case 3:
				menuConexiones();
				break;
			case 4:
				datos();
				break;
			case 5:
				puntoSeis();
				break;
			case 6:
				metadata();
				break;
			case 7:
				menuProcedimientosFunciones();
				break;
			case 8:
				listadoPDFreparaciones();
				break;
			case 0:
				System.out.println("Saliendo...");
				break;
			default:
				System.out.println("Opción inválida");
				break;
			}
		} while (opcion != 0);

		sc.close();
	}

	private static void seleccionarBaseDeDatos() {
		int op;
		do {
			System.out.println("Selecciona base de datos:");
			System.out.println("1. MySQL");
			System.out.println("2. Oracle");
			System.out.print("Opción: ");
			op = sc.nextInt();
			sc.nextLine();
		} while (op != 1 && op != 2);
		bdSeleccionada = (op == 1) ? TipoBD.MYSQL : TipoBD.ORACLE;
		System.out.println("Base de datos seleccionada: " + bdSeleccionada);
	}

	private static Connection obtenerConexion() throws SQLException, ClassNotFoundException {
		if (bdSeleccionada == TipoBD.MYSQL) {
			Class.forName("com.mysql.cj.jdbc.Driver");
			return DriverManager.getConnection(MYSQL_URL, MYSQL_USER, MYSQL_PASS);
		} else {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			return DriverManager.getConnection(ORACLE_URL, ORACLE_USER, ORACLE_PASS);
		}
	}

	public static void instalarBD() {
		try {
			Connection conexion = obtenerConexion();
			String archivo = (bdSeleccionada == TipoBD.MYSQL) ? "./script/accesoDatosmysql.sql"
					: "./script/accesoDatosoracle.sql";

			System.out.println("Ejecutando script desde: " + archivo);

			Reader reader = new FileReader(archivo);
			ScriptRunner scriptRunner = new ScriptRunner(conexion);
			scriptRunner.setLogWriter(null);
			scriptRunner.setAutoCommit(false);
			scriptRunner.setStopOnError(true);
			scriptRunner.runScript(reader);

			if (bdSeleccionada == TipoBD.ORACLE) {
				conexion.commit();
			}

			System.out.println("Script ejecutado con éxito (" + bdSeleccionada + ")");
			reader.close();
			conexion.close();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			System.err.println("Error al ejecutar script: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void eliminarBD() {
		try {
			Connection conexion = obtenerConexion();
			String archivo = (bdSeleccionada == TipoBD.MYSQL) ? "./script/drop_all_mysql.sql"
					: "./script/drop_all_oracle.sql";

			System.out.println("Ejecutando script de eliminación desde: " + archivo);

			Reader reader = new FileReader(archivo);
			ScriptRunner scriptRunner = new ScriptRunner(conexion);
			scriptRunner.setLogWriter(null);
			scriptRunner.setAutoCommit(false);
			scriptRunner.setStopOnError(true);
			scriptRunner.runScript(reader);

			if (bdSeleccionada == TipoBD.ORACLE) {
				conexion.commit();
			}

			System.out.println("Base de datos eliminada correctamente (" + bdSeleccionada + ")");
			reader.close();
			conexion.close();
		} catch (ClassNotFoundException | IOException | SQLException e) {
			System.err.println("Error al eliminar base de datos: " + e.getMessage());
			e.printStackTrace();
		}
	}

	public static void menuConexiones() {
		int op;
		do {
			System.out.println("--- CONEXIÓN ---");
			System.out.println("1. Probar conexión");
			System.out.println("0. Salir");
			System.out.print("Opción: ");
			op = sc.nextInt();
			sc.nextLine();

			switch (op) {
			case 1:
				probarConexion();
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida");
				break;
			}
		} while (op != 0);
	}

	public static void probarConexion() {
		try {
			Connection con = obtenerConexion();
			System.out.println("Conexión establecida con " + bdSeleccionada);

			String tabla = bdSeleccionada == TipoBD.MYSQL ? "SELECT 1 as conexion_ok"
					: "SELECT 1 as conexion_ok FROM dual";

			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery(tabla);
			if (rs.next()) {
				System.out.println("✓ Prueba de conexión exitosa");
			}

			rs.close();
			st.close();
			con.close();
		} catch (Exception e) {
			System.out.println("✗ Error al conectar con " + bdSeleccionada);
			e.printStackTrace();
		}
	}

	public static void datos() {
		int opciones;
		do {
			System.out.println("--- DATOS ---");
			System.out.println("1. Mostrar artículos");
			System.out.println("2. Mostrar clientes");
			System.out.println("3. Mostrar pedidos");
			System.out.println("4. Mostrar líneas de pedido");
			System.out.println("5. Insertar artículo");
			System.out.println("6. Modificar precio artículo");
			System.out.println("7. Eliminar artículo");
			System.out.println("0. Volver");
			System.out.print("Opción: ");
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
				System.out.println("Opción inválida");
				break;
			}
		} while (opciones != 0);
	}

	public static void puntoSeis() {
		int opciones;
		do {
			System.out.println("--- PUNTO 6 ---");
			System.out.println("1. Listado artículos por categoría");
			System.out.println("2. Insertar cliente");
			System.out.println("3. Modificar salario empleado");
			System.out.println("4. Reparaciones por estado");
			System.out.println("0. Volver");
			System.out.print("Opción: ");
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
				System.out.println("Opción inválida");
				break;
			}
		} while (opciones != 0);
	}

	private static void mostrarArticulos() {
		String sql = "SELECT id_articulo, nombre, categoria, precio, stock FROM articulos";
		try (Connection con = obtenerConexion();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				System.out.printf("%d | %s | %s | %.2f € | stock: %d%n", rs.getInt("id_articulo"),
						rs.getString("nombre"), rs.getString("categoria"), rs.getDouble("precio"), rs.getInt("stock"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mostrarClientes() {
		String sql = "SELECT id_cliente, nombre_completo, nif_cif, telefono, correo, ciudad FROM clientes";
		try (Connection con = obtenerConexion();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				System.out.printf("%d | %s | %s | %s | %s | %s%n", rs.getInt("id_cliente"),
						rs.getString("nombre_completo"), rs.getString("nif_cif"), rs.getString("telefono"),
						rs.getString("correo"), rs.getString("ciudad"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mostrarPedidosCabecera() {
		String sql = "SELECT id_pedido, id_cliente, id_empleado, id_tienda, fecha, estado FROM pedidos";
		try (Connection con = obtenerConexion();
				Statement st = con.createStatement();
				ResultSet rs = st.executeQuery(sql)) {
			while (rs.next()) {
				System.out.printf("%d | cliente:%d | vendedor:%d | tienda:%d | fecha:%s | estado:%s%n",
						rs.getInt("id_pedido"), rs.getInt("id_cliente"), rs.getInt("id_empleado"),
						rs.getInt("id_tienda"), rs.getDate("fecha"), rs.getString("estado"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void mostrarLineasPedido() {
		System.out.print("Introduce id del pedido: ");
		int idPedido = sc.nextInt();
		String sql = "SELECT id_linea, id_pedido, id_articulo, cantidad, precio_unitario FROM lineaspedido WHERE id_pedido = ?";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, idPedido);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.printf("%d | articulo:%d | cantidad:%d | precio:%.2f €%n", rs.getInt("id_linea"),
							rs.getInt("id_articulo"), rs.getInt("cantidad"), rs.getDouble("precio_unitario"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarArticulo() {
		System.out.print("Id Articulo: ");
		int ID = Integer.parseInt(sc.nextLine());
		System.out.print("Nombre: ");
		String nombre = sc.nextLine();
		System.out.print("Categoría: ");
		String categoria = sc.nextLine();
		System.out.print("Precio: ");
		double precio = Double.parseDouble(sc.nextLine());
		System.out.print("Stock: ");
		int stock = Integer.parseInt(sc.nextLine());
		System.out.print("Proveedor id (0 si ninguno): ");
		int proveedorId = Integer.parseInt(sc.nextLine());
		String sql = "INSERT INTO articulos (id_articulo, nombre, categoria, precio, stock, id_proveedor) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = obtenerConexion();
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
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void modificarPrecioArticulo() {
		System.out.print("Id artículo: ");
		int id = sc.nextInt();
		System.out.print("Nuevo precio: ");
		double nuevoPrecio = sc.nextDouble();
		sc.nextLine();
		String sql = "UPDATE articulos SET precio = ? WHERE id_articulo = ?";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, nuevoPrecio);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void eliminarArticulo() {
		System.out.print("Id artículo: ");
		int id = sc.nextInt();
		sc.nextLine();
		String checkSql = "SELECT COUNT(*) AS cnt FROM lineaspedido WHERE id_articulo = ?";
		String deleteSql = "DELETE FROM articulos WHERE id_articulo = ?";
		try (Connection con = obtenerConexion(); PreparedStatement psCheck = con.prepareStatement(checkSql)) {
			psCheck.setInt(1, id);
			try (ResultSet rs = psCheck.executeQuery()) {
				if (rs.next() && rs.getInt("cnt") > 0) {
					System.out.println("No se puede eliminar");
					return;
				}
			}
			try (PreparedStatement psDel = con.prepareStatement(deleteSql)) {
				psDel.setInt(1, id);
				psDel.executeUpdate();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void articulosPorCategoria() {
		System.out.print("Categoría: ");
		String categoria = sc.nextLine();
		String sql = "SELECT id_articulo, nombre, categoria, precio, stock, id_proveedor FROM articulos WHERE categoria = ?";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, categoria);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.printf("%d | %s | %s | %.2f € | %d | %d%n", rs.getInt("id_articulo"),
							rs.getString("nombre"), rs.getString("categoria"), rs.getDouble("precio"),
							rs.getInt("stock"), rs.getInt("id_proveedor"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void insertarNuevoCliente() {
		System.out.print("Id cliente: ");
		int id_cliente = Integer.parseInt(sc.nextLine());
		System.out.print("Nombre completo: ");
		String nombre_completo = sc.nextLine();
		System.out.print("NIF/CIF: ");
		String nif_cif = sc.nextLine();
		System.out.print("Teléfono: ");
		int telefono = Integer.parseInt(sc.nextLine());
		System.out.print("Correo: ");
		String correo = sc.nextLine();
		System.out.print("Ciudad: ");
		String ciudad = sc.nextLine();
		String sql = "INSERT INTO clientes (id_cliente, nombre_completo, nif_cif, telefono, correo, ciudad) VALUES (?, ?, ?, ?, ?, ?)";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setInt(1, id_cliente);
			ps.setString(2, nombre_completo);
			ps.setString(3, nif_cif);
			ps.setInt(4, telefono);
			ps.setString(5, correo);
			ps.setString(6, ciudad);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void modificarSalarioEmpleado() {
		System.out.print("Id empleado: ");
		int id = sc.nextInt();
		System.out.print("Nuevo salario: ");
		double nuevoSalario = sc.nextDouble();
		sc.nextLine();
		String sql = "UPDATE empleados SET salario = ? WHERE id_empleado = ?";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setDouble(1, nuevoSalario);
			ps.setInt(2, id);
			ps.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void reparacionesPorEstado() {
		System.out.print("Estado: ");
		String estado = sc.nextLine();
		String sql = "SELECT id_reparacion, id_cliente, id_empleado, id_tienda, fecha_entrada, fecha_salida, descripcion, diagnostico, coste_mano_obra, estado FROM reparaciones WHERE estado = ?";
		try (Connection con = obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {
			ps.setString(1, estado);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next()) {
					System.out.printf(
							"%d | cliente:%d | empleado:%d | tienda:%d | entrada:%s | salida:%s | desc:%s | diag:%s | coste:%.2f € | estado:%s%n",
							rs.getInt("id_reparacion"), rs.getInt("id_cliente"), rs.getInt("id_empleado"),
							rs.getInt("id_tienda"), rs.getDate("fecha_entrada"), rs.getDate("fecha_salida"),
							rs.getString("descripcion"), rs.getString("diagnostico"), rs.getDouble("coste_mano_obra"),
							rs.getString("estado"));
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private static void metadata() {
		try {
			Connection conexion = obtenerConexion();
			DatabaseMetaData dbmd = conexion.getMetaData();
			System.out.printf("Nombre: %s%nDriver: %s%nURL: %s%nUsuario: %s%n", dbmd.getDatabaseProductName(),
					dbmd.getDriverName(), dbmd.getURL(), dbmd.getUserName());
			ResultSet resul = dbmd.getTables(null, null, null, new String[] { "TABLE" });
			while (resul.next()) {
				String nombreTabla = resul.getString("TABLE_NAME");
				System.out.println("Tabla: " + nombreTabla);
			}
			conexion.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void menuProcedimientosFunciones() {
		int opcion;
		do {
			System.out.println("1. Subir precios categoría\n2. Calcular importe pedido\n0. Volver");
			System.out.print("Opción: ");
			opcion = sc.nextInt();
			sc.nextLine();

			switch (opcion) {
			case 1:
				subirPreciosCategoria();
				break;
			case 2:
				calcularImportePedido();
				break;
			case 0:
				break;
			default:
				System.out.println("Opción inválida");
				break;
			}
		} while (opcion != 0);
	}

	public static void subirPreciosCategoria() {
		try {
			System.out.print("Categoría: ");
			String categoria = sc.nextLine();
			System.out.print("Porcentaje: ");
			double porcentaje = sc.nextDouble();
			sc.nextLine();
			Connection con = obtenerConexion();
			String sql = "UPDATE articulos SET precio = precio * (1 + ?/100) WHERE categoria = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setDouble(1, porcentaje);
			ps.setString(2, categoria);
			int filas = ps.executeUpdate();
			System.out.println("Filas actualizadas: " + filas);
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void calcularImportePedido() {
		try {
			System.out.print("ID pedido: ");
			int idPedido = sc.nextInt();
			sc.nextLine();
			Connection con = obtenerConexion();
			String sql = "SELECT SUM(cantidad * precio_unitario) AS total FROM lineaspedido WHERE id_pedido = ?";
			PreparedStatement ps = con.prepareStatement(sql);
			ps.setInt(1, idPedido);
			ResultSet rs = ps.executeQuery();
			if (rs.next())
				System.out.println("Total: " + rs.getDouble("total"));
			else
				System.out.println("No se encontró el pedido");
			rs.close();
			ps.close();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void listadoPDFreparaciones() {
		String miplantilla = "C:\\Users\\windh\\eclipse-workspace\\conexionmysql\\src\\main\\java\\proyectoCapituloDos\\informe\\plantillaReparaciones.jrxml";
		String reportHTML = "C:\\Users\\windh\\eclipse-workspace\\conexionmysql\\src\\main\\java\\proyectoCapituloDos\\terminado\\InformeReparaciones.html";
		String reportPDF = "C:\\Users\\windh\\eclipse-workspace\\conexionmysql\\src\\main\\java\\proyectoCapituloDos\\terminado\\InformeReparaciones.pdf";
		String reportXML = "C:\\Users\\windh\\eclipse-workspace\\conexionmysql\\src\\main\\java\\proyectoCapituloDos\\terminado\\InformeReparaciones.xml";
		Map<String, Object> params = new HashMap<>();
		params.put("titulo", "LISTADO DE REPARACIONES");
		params.put("autor", "Manuel y Ariadne");
		try {
			Connection conn = obtenerConexion();
			JasperReport jasperReport = JasperCompileManager.compileReport(miplantilla);
			JasperPrint miInforme = JasperFillManager.fillReport(jasperReport, params, conn);
			JasperViewer.viewReport(miInforme, false);
			JasperExportManager.exportReportToHtmlFile(miInforme, reportHTML);
			JasperExportManager.exportReportToPdfFile(miInforme, reportPDF);
			JasperExportManager.exportReportToXmlFile(miInforme, reportXML, false);
			conn.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
