package com.ryo.xolo.utils.sepomex.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.lang3.StringUtils;

import com.ryo.xolo.utils.sepomex.parser.domain.Asentamiento;
import com.ryo.xolo.utils.sepomex.parser.domain.DataClasifier;
import com.ryo.xolo.utils.sepomex.parser.domain.Municipio;
import com.ryo.xolo.utils.sepomex.parser.domain.TipoAsentamiento;

public class ApplicationSepomexParser {

	public final static String COMMENT_IDENTIFIER = "#";
	public final static String HEADER_IDENTIFIER = ">";
	public final static String TOKEN_PARSER = "\\|";
	public final static String FILE_NAME = "./files/cat_nac_codigos_postales.txt";

	public static void main(String[] args) {
		String nombreArchivo = ApplicationSepomexParser.FILE_NAME;

		try (Stream<String> lineas = obtenerLineas(nombreArchivo)) {
			List<String> lineasProcesadas = lineas
					.map(linea -> procesarLinea(linea)) // Aplicar algún procesamiento a cada línea
					.collect(Collectors.toList());

			depurarLineas(lineasProcesadas);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static Stream<String> obtenerLineas(String nombreArchivo) throws IOException {
		// Utilizar ClassLoader para cargar el archivo desde src/main/resources
		InputStream inputStream = ApplicationSepomexParser.class.getClassLoader().getResourceAsStream(nombreArchivo);

		if (inputStream == null) {
			throw new IOException("No se pudo cargar el archivo: " + nombreArchivo);
		}

		BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
		return lector.lines();
	}

	private static String procesarLinea(String linea) {
		if (linea.startsWith(COMMENT_IDENTIFIER)) {
			return linea;
		} else if (linea.startsWith(HEADER_IDENTIFIER)) {
			return linea;
		} else {
			return linea.toUpperCase();
		}
	}

	private static DataClasifier depurarLineas(List<String> lines) {
		// Clasificar las lineas
		final DataClasifier clasificador = new DataClasifier();
		lines.forEach(lineIt -> {
			if (lineIt.startsWith(COMMENT_IDENTIFIER)) {
				// Skip line
			} else if (lineIt.startsWith(HEADER_IDENTIFIER)) {
				final String lineHeader = lineIt.replace(HEADER_IDENTIFIER, StringUtils.EMPTY);
				clasificador.setHeader(Arrays.asList(lineHeader.split(TOKEN_PARSER)));
			} else {
				clasificador.getData().add(lineIt.split(TOKEN_PARSER));
			}
		});
		// Listado de datos.
		final HashMap<Integer, String> hmTipoAsentamientos = new HashMap<>();
		final HashMap<Integer, String> hmMunicipios = new HashMap<>();
		final HashMap<Integer, String> hmAsentamiento = new HashMap<>();

		// Depurar datos.
		clasificador.getData().forEach(lineIt -> {
			// Tipo Asentamiento.d_tipo_asenta,c_tipo_asenta
			 int idxIndTipoAsentamiento = clasificador.getHeader().indexOf("c_tipo_asenta");
			 int idxDescTipoAsentamiento = clasificador.getHeader().indexOf("d_tipo_asenta");
			 int idTipoAsentamiento = Integer.parseInt(lineIt[idxIndTipoAsentamiento]);
			 hmTipoAsentamientos.put(idTipoAsentamiento, new TipoAsentamiento(idTipoAsentamiento, lineIt[idxDescTipoAsentamiento]).toSqlInsert());
			
			// Municipios. D_mnpio c_mnpio c_estado
			 int idxIdMunicipio = clasificador.getHeader().indexOf("c_mnpio");
			 int idxDescMunicipio = clasificador.getHeader().indexOf("D_mnpio");
			 int idxIdEdoMunicipio = clasificador.getHeader().indexOf("c_estado");
			 int idMunicipio = Integer.parseInt(lineIt[idxIdMunicipio]);
			 hmMunicipios.put(idMunicipio, new Municipio(idMunicipio, lineIt[idxDescMunicipio], Integer.parseInt(lineIt[idxIdEdoMunicipio])).toSqlInsert());
			 
			 //Asentamiento d_asenta d_codigo id_asenta_cpcons
			 int idxDescAsentamiento = clasificador.getHeader().indexOf("d_asenta");
			 int idxDescCodPos = clasificador.getHeader().indexOf("d_codigo");
			 int idxIdAsentamiento = clasificador.getHeader().indexOf("id_asenta_cpcons");
			 int idAsentamiento = Integer.parseInt(lineIt[idxIdAsentamiento]);
			 hmAsentamiento.put(idAsentamiento, new Asentamiento(idAsentamiento, lineIt[idxDescAsentamiento], lineIt[idxDescCodPos],idTipoAsentamiento, idMunicipio).toSqlInsert());
		});
		
		// Println
		for (String valor : hmTipoAsentamientos.values()) {
            System.out.println(valor);
        }
		
		for (String valor : hmMunicipios.values()) {
            System.out.println(valor);
        }
		
		for (String valor : hmAsentamiento.values()) {
            System.out.println(valor);
        }
		
		return clasificador;
	}
}
