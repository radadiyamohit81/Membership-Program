# 📄 pom.xml — What is it and how is it structured?

## Q: What is pom.xml?

`pom.xml` stands for **Project Object Model**.  
It is the **heart of a Maven project** — it tells Maven:
- What your project is (groupId, artifactId, version)
- What libraries it needs (dependencies)
- How to build it (plugins)

---

## Structure Breakdown

```xml
<?xml version="1.0.0" encoding="UTF-8"?>
<project>

    <modelVersion>4.0.0</modelVersion>       <!-- Always 4.0.0 for Maven -->

    <!-- Inherits Spring Boot defaults (versions, plugin config) -->
    <parent>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-parent</artifactId>
        <version>3.4.1</version>
        <relativePath/>
    </parent>

    <!-- Your project identity -->
    <groupId>com.firstclub</groupId>         <!-- like a package/org name -->
    <artifactId>firstclub</artifactId>       <!-- your project/module name -->
    <version>0.0.1-SNAPSHOT</version>        <!-- SNAPSHOT = in development -->
    <packaging>jar</packaging>               <!-- output format -->

    <!-- Java version, encoding settings -->
    <properties>
        <java.version>21</java.version>
        <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
    </properties>

    <!-- Libraries you want to use -->
    <dependencies>
        ...
    </dependencies>

    <!-- How to build the project -->
    <build>
        <plugins>
            ...
        </plugins>
    </build>

</project>
```

---

## Key Terms

| Term | Meaning |
|------|---------|
| `groupId` | Organisation/package identifier (reverse domain: `com.firstclub`) |
| `artifactId` | Project/module name |
| `version` | Your app's version. `SNAPSHOT` = not yet released |
| `packaging` | Output type: `jar` (runnable) or `war` (server-deployed) |
| `scope` | When is this dependency needed? `runtime` = only at run, `test` = only in tests |

---

## Why `<parent>`?

Spring Boot parent manages **compatible versions** of all Spring libraries.  
You don't need to write version numbers for Spring dependencies — the parent picks the right one.

> Without parent → you manage versions manually → version conflict hell  
> With parent → Spring guarantees all versions are compatible ✓
