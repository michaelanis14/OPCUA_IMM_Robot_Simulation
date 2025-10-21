# Build Instructions

## Prerequisites

- Java 11 or higher
- Maven 3.6.0 or higher
- open62541 native library (libopen62541.so on Linux, open62541.dll on Windows)

## Building the Project

### 1. Verify Prerequisites

```bash
# Check Java version
java -version

# Check Maven version
mvn -version
```

### 2. Clean Build

```bash
# Clean previous builds
mvn clean

# Compile and package
mvn package
```

### 3. Run Tests

```bash
# Run unit tests only
mvn test

# Run all tests (unit + integration)
mvn verify

# Run tests with coverage report
mvn clean verify
# Coverage report will be in: target/site/jacoco-aggregate/index.html
```

### 4. Build Without Tests

```bash
mvn package -DskipTests
```

### 5. Generate Fat JAR

```bash
mvn clean package
# Fat JAR will be created: target/opcua-imm-robot-simulation-1.0.0-SNAPSHOT-jar-with-dependencies.jar
```

## Running the Application

### Run Molding Machine Controller

```bash
java -cp target/opcua-imm-robot-simulation-1.0.0-SNAPSHOT.jar controller.MoldingMachineController
```

### Run Robot Controller

```bash
java -cp target/opcua-imm-robot-simulation-1.0.0-SNAPSHOT.jar controller.RobotController
```

### Run Main Application (Both Controllers)

```bash
java -jar target/opcua-imm-robot-simulation-1.0.0-SNAPSHOT.jar
```

## Development

### IDE Setup

#### Eclipse
1. Import as Maven project: File → Import → Maven → Existing Maven Projects
2. Select the root directory
3. Eclipse will automatically configure the project

#### IntelliJ IDEA
1. Open the project directory
2. IntelliJ will automatically detect the Maven project
3. Trust the project and let Maven import complete

#### VS Code
1. Install Java Extension Pack
2. Open the project folder
3. Maven will be automatically detected

### Useful Maven Commands

```bash
# Show dependency tree
mvn dependency:tree

# Update dependencies
mvn versions:display-dependency-updates

# Format code (if formatter plugin added)
mvn spotless:apply

# Generate site documentation
mvn site

# Install to local repository
mvn install

# Deploy to remote repository
mvn deploy
```

## Native Library Setup

### Linux

```bash
# Install open62541
sudo apt-get install libopen62541-dev

# Or compile from source
git clone https://github.com/open62541/open62541.git
cd open62541
mkdir build && cd build
cmake -DBUILD_SHARED_LIBS=ON ..
make
sudo make install
sudo ldconfig
```

### macOS

```bash
# Install open62541
brew install open62541
```

### Windows

1. Download open62541 pre-built binaries or compile from source
2. Place `open62541.dll` in system PATH or application directory
3. Set `java.library.path` when running:
   ```
   java -Djava.library.path=C:\path\to\dll -jar target\opcua-imm-robot-simulation-1.0.0-SNAPSHOT.jar
   ```

## Test Coverage

The project enforces minimum test coverage:
- Line Coverage: 80%
- Branch Coverage: 75%

View coverage report after running `mvn verify`:
- Open `target/site/jacoco-aggregate/index.html` in browser

## Troubleshooting

### UnsatisfiedLinkError

If you get `UnsatisfiedLinkError` for native library:
```bash
# Set library path explicitly
java -Djava.library.path=/usr/local/lib -jar target/opcua-imm-robot-simulation-1.0.0-SNAPSHOT.jar
```

### Tests Failing

```bash
# Run with detailed output
mvn test -X

# Run specific test
mvn test -Dtest=MoldingMachineTest

# Skip integration tests
mvn verify -DskipITs
```

### Port Already in Use

If ports 4840 or 4050 are in use:
- Edit `src/main/resources/application.yaml` to change ports
- Or kill the process using the port:
  ```bash
  # Linux/Mac
  lsof -ti:4840 | xargs kill -9
  ```

## Continuous Integration

The project is configured for CI/CD with:
- Automated testing on every commit
- Code coverage reporting
- Build artifact generation

See `.github/workflows/` for CI configuration (if using GitHub Actions).
