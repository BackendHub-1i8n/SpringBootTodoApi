## 🌿 Estructura de Ramas GIT Sugerida

Para reflejar la jerarquía Epic → Sub-Issue, proponemos organizar las ramas de la siguiente manera:

1. **`main`** (o `master`)

   * Rama estable con la versión de producción.

2. **`develop`**

   * Rama de integración continua donde se fusionan las features completadas antes de preparar un release.

3. **`epic/`**

   * Ramas de historia principal (Epic). Nombre recomendado:

     * `epic/<EPIC-ID>-<título-corto>`
     * Ejemplo: `epic/001-migracion-db-real`

4. **`feature/`**

   * Ramas derivadas de la Epic, una por cada sub-issue. Nombre recomendado:

     * `feature/<EPIC-ID>/<ISSUE-ID>-<descripción-corta>`
   * Ejemplos para la Epic `001`:

     * `feature/001/001-analisis-diseno-persistencia`
     * `feature/001/002-config-db-dev`
     * `feature/001/003-integra-db-spring`
     * `feature/001/004-refactor-servicios-jpa`
     * `feature/001/005-seeders-carga-datos`
     * `feature/001/006-limpieza-dummy-db`
     * `feature/001/007-tests-integracion-db`
     * `feature/001/008-config-prod-db`
     * `feature/001/009-doc-migracion`
     * `feature/001/010-validacion-final`

5. **`release/`**

   * Ramas de preparación de versiones (opcional): `release/v1.0.0`.

6. **`hotfix/`**

   * Para correcciones urgentes en producción: `hotfix/<issue-id>-<descripción>`.

### Flujo de trabajo adaptado

* Cuando inicies la Epic, crea `epic/001-migracion-db-real` a partir de `develop`.
* Para cada sub-issue, crea su rama `feature/001/<ISSUE-ID>-...` basada en la rama `epic/001-migracion-db-real`.
* Completa la sub-issue y solicita un PR a `epic/001-migracion-db-real`.
* Una vez todas las sub-issues estén fusionadas en la rama Epic, realiza un PR de `epic/001-migracion-db-real` a `develop`.
* Cuando `develop` esté listo para release, crea `release/vX.Y.Z`, prueba y fusiona en `main`, luego regresa a `develop`.
* Para hotfix en producción, crea la rama desde `main`, corrige y fusiona en `main` y `develop`.

Esta estructura te permite identificar de un vistazo si la rama corresponde a la Epic (super-tarea) o a una sub-issue, manteniendo claridad y trazabilidad.
