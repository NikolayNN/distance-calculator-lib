# distance-calculator-lib

Библиотека для расчёта расстояний и упрощения GPS-треков. Требуемая версия Java для приложения — 11 или выше.

## Подключение через GitHub Packages

Для перехода с JitPack замените репозиторий и координаты зависимости в POM приложения. Пример для первого релиза после миграции (после его публикации):

```xml
<repositories>
    <repository>
        <id>github</id>
        <url>https://maven.pkg.github.com/nikolaynn/distance-calculator-lib</url>
        <snapshots><enabled>true</enabled></snapshots>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>by.nhorushko</groupId>
        <artifactId>distance-calculator-lib</artifactId>
        <version>7.8</version>
    </dependency>
</dependencies>
```

Для сборки из ветки `develop` укажите `7.8-SNAPSHOT`. Для чтения пакета задайте переменные окружения `GH_PACKAGES_USER` (логин GitHub) и `GH_PACKAGES_TOKEN` (personal access token classic с правом `read:packages` и доступом к репозиторию). Вставьте в раздел `<servers>` файла `~/.m2/settings.xml`:

```xml
<server>
    <id>github</id>
    <username>${env.GH_PACKAGES_USER}</username>
    <password>${env.GH_PACKAGES_TOKEN}</password>
</server>
```

## Сборка

```sh
mise trust
mise install
mise exec -- mvn -B clean verify
```

Сборка использует Temurin 25, Maven 3.9.16 и компилирует библиотеку с `--release 11`. В CI та же версия Maven запускается через `./mvnw`.

## Релиз и версии

В `pom.xml` хранится разрабатываемая версия. CI проверяет pull request в `develop` и `master`. После push в `develop` CI добавляет к версии суффикс `-SNAPSHOT` в своей рабочей копии и публикует snapshot в GitHub Packages.

Запустите `/aurora-release` из `develop`. Процедура закрывает раздел «Не выпущено» в `CHANGELOG.md`, переводит `master` на релизный коммит через fast-forward и открывает следующую версию в `develop`. После push в `master` CI проверяет тесты и отсутствие тега и пакета этой версии, публикует JAR и POM, затем ставит тег `v<версия>` на релизный коммит. Для публикации используется штатный `GITHUB_TOKEN` репозитория.
