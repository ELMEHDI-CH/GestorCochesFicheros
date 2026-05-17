# Sistema Avanzado de Gestión de Vehículos e Inventario de Automóviles

Este proyecto ha sido desarrollado como una solución de software orientada a objetos para el módulo profesional de **Programación**. Consiste en una aplicación de consola en Java diseñada para administrar de forma eficiente una flota de vehículos, implementando estrategias avanzadas de persistencia de datos mediante el análisis de archivos de texto plano (CSV) y la serialización/deserialización de objetos en archivos binarios de acceso local.

---

## 1. Descripción General del Proyecto

La aplicación resuelve un problema clásico en el desarrollo de software: **la persistencia del estado de una aplicación**. En las aplicaciones básicas de consola, los datos introducidos por el usuario residen únicamente en la memoria volátil (RAM) y se pierden por completo al finalizar la ejecución del programa. 

Para solventar esta limitación, este sistema implementa un modelo de persistencia híbrido basado en dos flujos de trabajo independientes y complementarios:

1. **Importación y Migración Masiva (Origen CSV):** Permite poblar el sistema de manera automatizada a partir de un archivo estructurado independiente (`coches.csv`). Este mecanismo simula un proceso real de migración de datos o cargas masivas desde hojas de cálculo o sistemas externos de terceros.
2. **Persistencia del Estado del Sistema (Destino Binario):** Permite al usuario salvar el estado exacto del grafo de objetos de la aplicación en un archivo binario en el disco local (`.dat` o `.bin`). Al reiniciar la aplicación, el usuario puede restaurar de inmediato toda la información guardada sin necesidad de volver a procesar el archivo de texto plano original, asegurando la integridad de los tipos de datos nativos de Java.

---

## 2. Arquitectura del Código y Diseño de Clases

El código fuente se rige bajo los principios de la Programación Orientada a Objetos (POO), garantizando una separación clara de responsabilidades (Single Responsibility Principle). A continuación se detalla la función analítica y técnica de cada uno de los componentes del software:

### 2.1. Clase Modelo: `Coche.java`
Es una clase de tipo POJO (Plain Old Java Object) que actúa como la entidad fundamental del dominio del problema. 
* **Atributos:** Define el estado del coche mediante variables privadas protegidas bajo el principio de **encapsulamiento** (por ejemplo: `id`, `marca`, `modelo`, `matricula`, `precio`, `kilometraje`, etc.).
* **Encapsulamiento:** El acceso a los datos está estrictamente controlado mediante métodos públicos de acceso y modificación (`getters` y `setters`).
* **Serialización:** Implementa la interfaz marcadora `java.io.Serializable`. Este es un requisito técnico obligatorio para permitir que la máquina virtual de Java (JVM) pueda aplanar las instancias de esta clase y transformarlas en una secuencia de bytes apta para ser grabada en el disco duro.
* **Representación en Texto:** Reescribe (`@Override`) el método `toString()` para formatear de manera elegante y legible la salida de los datos del coche en la interfaz de usuario de la consola.

### 2.2. Componente de Entrada de Datos: `Importadorficheros.java`
Esta clase implementa la lógica de importación y procesamiento (parsing) de archivos de texto plano.
* **Mecanismo de Lectura:** Utiliza flujos de caracteres eficientes mediante las clases `FileReader` y `BufferedReader`. El uso de `BufferedReader` permite leer el archivo línea por línea mediante el método `readLine()`, lo cual optimiza notablemente el consumo de memoria al no cargar todo el archivo de texto de forma masiva en la RAM.
* **Procesamiento de Tokens:** Cada línea leída se descompone en sus campos constituyentes utilizando el método `.split(",")`, empleando la coma como carácter delimitador estándar del formato CSV.
* **Transformación y Casting:** Convierte las cadenas de texto del CSV en los tipos de datos nativos de los atributos del objeto (ej. conversión de texto a enteros mediante `Integer.parseInt()` o a decimales con `Double.parseDouble()`). Instancia nuevos objetos `Coche` y los almacena dinámicamente dentro de una estructura de lista (`List<Coche>`).
* **Robustez y Gestión de Excepciones:** Cuenta con bloques `try-catch` para interceptar de manera controlada errores críticos como `FileNotFoundException` (si el archivo no existe) o `IOException` (fallos generales de lectura).

### 2.3. Componente de Persistencia Segura: `GestorArchivoBinario.java`
Esta clase encapsula toda la lógica de persistencia a bajo nivel empleando flujos binarios nativos de objetos.
* **Operación de Guardado (Escritura):** Enlaza un flujo de salida de archivo (`FileOutputStream`) con un filtro de serialización de objetos (`ObjectOutputStream`). Permite volcar la estructura de datos al completo (ej. una lista de coches `List<Coche>`) en un archivo binario mediante un único método de llamada: `writeObject()`.
* **Operación de Carga (Lectura):** Realiza el proceso inverso (deserialización) enlazando un `FileInputStream` con un `ObjectInputStream`. Recupera el flujo de bytes desde el disco y reconstruye el grafo de objetos exacto en memoria RAM mediante el método `readObject()`.
* **Gestión de Excepciones Especializadas:** Además de controlar las excepciones de entrada y salida estándar, maneja explícitamente la excepción `ClassNotFoundException`, necesaria al deserializar objetos cuyo archivo de clase de definición estructural pudiera no estar disponible en el *classpath* de la aplicación.

### 2.4. Controlador Central y Entrada de Usuario: `Main.java`
Constituye el punto de entrada principal del programa ejecutable (`public static void main`).
* **Orquestación:** Alberga la colección dinámica en memoria (típicamente un `ArrayList<Coche>`) que actúa como base de datos temporal durante el ciclo de vida de la ejecución.
* **Interfaz de Usuario (CLI):** Implementa un menú interactivo basado en un bucle repetitivo de control (`do-while` o `while`) y una estructura de selección múltiple (`switch-case`).
* **Captura de Datos:** Utiliza la clase `java.util.Scanner` para capturar de manera interactiva los comandos elegidos por el usuario, coordinando las invocaciones lógicas hacia los componentes del `Importadorficheros` y el `GestorArchivoBinario` de acuerdo a las peticiones del operador.

---

## 3. Arquitectura del Sistema y Flujos de Datos

### 3.1. Diagrama de Clases Estructural (Estructural UML en formato ASCII)

```
+-------------------------------------------------------------+
|                            Main                             |
+-------------------------------------------------------------+
| - listaCoches : List<Coche>                                 |
+-------------------------------------------------------------+
| + main(args : String[])                                     |
+-------------------------------------------------------------+
                               |
        +----------------------+----------------------+
        |                                             |
        v                                             v
+-----------------------------+               +-----------------------------+
|     Importadorficheros      |               |    GestorArchivoBinario     |
+-----------------------------+               +-----------------------------+
| + importar(ruta: String)    |               | + guardar(lista: List)       |
|   : List<Coche>             |               | + cargar(ruta: String): List|
+-----------------------------+               +-----------------------------+
        |                                                     |
        | (Instancia)                                         | (Manipula e Inyecta)
        +----------------------+------------------------------+
                               |
                               v
+-------------------------------------------------------------+
|                           Coche                             |
+-------------------------------------------------------------+
| - id : int                                                  |
| - marca : String                                            |
| - modelo : String                                           |
| - matricula : String                                        |
| - precio : double                                           |
+-------------------------------------------------------------+
| + Coche(...)                                                |
| + getters / setters()                                       |
| + toString() : String                                       |
+-------------------------------------------------------------+
```

### 3.2. Arquitectura de Almacenamiento y Ciclo de Vida del Dato

El siguiente esquema ilustra cómo los datos fluyen a través de las diferentes capas físicas e lógicas de almacenamiento del sistema:

```
[ Capa de Datos Externa ] ──> ( coches.csv )
                                    │
                                    │ (Método importar con BufferedReader)
                                    ▼
[ Capa de Aplicación ]    ──> [ Memoria Volátil RAM (List<Coche>) ]
                                    │ ▲
               (ObjectOutputStream) │ │ (ObjectInputStream)
                                    ▼ │
[ Capa de Almacenamiento ] ──> ( archivo_datos.dat ) <--- [Persistencia Binaria]
```

---

## 4. Manual de Usuario y Guía de Funcionamiento

Para interactuar con la aplicación de forma adecuada, siga los flujos operacionales del sistema descritos a continuación basados en las capturas reales de la consola de comandos.

### Paso 1: Menú Principal y Control de Excepciones (Carga Inicial)
Al arrancar la aplicación, el sistema despliega el menú interactivo con las opciones de gestión del inventario. El software cuenta con un control robusto de errores mediante bloques `try-catch`. Si el usuario intenta realizar acciones operativas antes de que el archivo `coches.csv` se encuentre situado en la raíz del proyecto, el sistema captura de forma segura la excepción `FileNotFoundException`. De este modo, en lugar de colapsar la aplicación, el programa informa de manera controlada que existen `0 coches cargados`, manteniendo la ejecución activa y estable.

![Menú de la Aplicación y Control de Errores](importardatos.png)

### Paso 2: Lectura Analítica y Procesamiento de Ficheros
Una vez que el archivo de datos estructurado se encuentra en el directorio correcto y el usuario ejecuta la acción del menú, la clase `Importadorficheros` procesa las líneas mediante `BufferedReader`. El sistema interactúa de forma modular con la clase `CalculadorPrecio` para aplicar dinámicamente los porcentajes de impuestos (21% o 10% según la antigüedad del vehículo en base al año actual 2026) y los descuentos correspondientes antes de instanciar los objetos en memoria.

![Procesamiento de Archivos en Consola](listar_coches.png)

### Paso 3: Persistencia Segura en Almacenamiento Secundario (Guardado Binario)
Para evitar la pérdida de la información procesada tras cerrar la consola, el usuario puede ejecutar la opción de guardado. El programa invoca a la clase `GestorArchivoBinario` la cual, mediante flujos de datos (`FileOutputStream` y `DataOutputStream`), escribe de forma secuencial y ordenada los atributos nativos de los vehículos (marca, modelo, año, kilómetros, precio base, descuento, color, combustible, transmisión, estado y precio final). El sistema genera un archivo binario local optimizado, garantizando la persistencia permanente de los datos de la flota.

![Guardado de Datos en Archivo Binario](guardar_coches.png)
### Paso 3: Persistencia Segura en Almacenamiento Secundario (Guardado Binario)
Para evitar la pérdida de la información procesada tras cerrar la consola, el usuario puede ejecutar la opción de guardado. El programa invoca a la clase `GestorArchivoBinario` la cual, mediante flujos de datos (`FileOutputStream` y `DataOutputStream`), escribe de forma secuencial y ordenada los atributos nativos de los vehículos (marca, modelo, año, kilómetros, precio base, descuento, color, combustible, transmisión, estado y precio final). El sistema genera un archivo binario local optimizado, garantizando la persistencia permanente de los datos de la flota.

![Guardado de Datos en Archivo Binario](img/guardar_coches.png)
