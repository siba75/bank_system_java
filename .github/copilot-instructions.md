## Copilot / AI Agent Instructions — SE3 Java Project

Purpose: Quickly orient an AI contributor so changes are safe, buildable, and consistent.

- **Big picture**: This repository is a small Java banking domain demonstrating design patterns. Core packages under `src`:
  - `model`: domain objects and many canonical implementations (e.g., `Account`, `AccountDecorator`, `CompositeAccount`, `Customer`).
  - `decorator`: runtime decorator implementations (wrappers that add features like overdraft protection).
  - `strategy`: interest calculation strategies (`InterestStrategy`, `SavingsInterest`, `LoanInterest`, `CurrentInterest`).
  - `service`: application-level services (e.g., `AuthService`, `service/account/AccountFactory`).
  - `test`: JUnit tests (JUnit 5 standalone jar provided in `lib`).

- **Why this layout**: `model` contains the domain and abstractions; `decorator` contains concrete decorator classes used by callers/tests. Some concepts appear in both places (same-named classes in `model` and `decorator`) — do not rename packages or move classes without verifying imports and tests.

- **Important files to inspect before changing behavior**:
  - `src/model/Account.java` — core interface for accounts.
  - `src/model/AccountDecorator.java` — base decorator that decorators extend.
  - `src/decorator/OverdraftProtection.java` and `src/model/OverdraftProtection.java` — example of parallel implementations; tests import `decorator.*`.
  - `src/strategy/InterestStrategy.java` and implementations in `src/strategy/`.
  - `src/service/account/AccountFactory.java` — factory + integration touchpoint.

- **Patterns & conventions (repo-specific)**:
  - Package-to-directory mapping is strict: keep `package` declarations consistent with `src/<package-path>`.
  - Decorators inherit `AccountDecorator` and delegate to `account` field; use `displayInfo()`, `deposit()`, `withdraw()` as the primary extension points.
  - Strategy implementations are small, stateless classes implementing `InterestStrategy` — update only strategy classes for interest behavior.
  - Observer pattern: `AccountSubject`, `AccountObserver`, and `EmailNotifier` implement notification flows; prefer using these utilities rather than ad-hoc printlns for events.

- **Build & test (how to run locally)**
  - Compile all sources into `bin` (PowerShell):

```powershell
javac -d bin -cp "lib/*" src\**\*.java
```

  - Run unit tests (JUnit Platform standalone jar in `lib`):

```powershell
java -jar lib\junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path
```

  - Unix / bash variants (if using WSL or similar):

```bash
javac -d bin -cp 'lib/*' src/**/*.java
java -jar lib/junit-platform-console-standalone-1.10.0.jar --class-path bin --scan-class-path
```

- **Testing notes**:
  - Tests use JUnit 5; the project includes the standalone runner jar in `lib`. Tests are in `src/test` and are package-less — ensure tests are compiled and on the classpath when running the runner.
  - If adding new tests, place them under `src/test` and import JUnit Jupiter APIs as shown in `src/test/AccountTests.java`.

- **Editing guidance**:
  - When changing core behavior, modify `src/model/*` first; if there is a matching class in `src/decorator` ensure callers/tests still import the expected package.
  - Keep changes minimal and focused. Avoid moving classes between packages because package names are relied on by imports throughout tests and other code.
  - Preserve existing printed messages used by tests (some tests/confidence checks may rely on console output patterns).

- **Cross-component integration points**:
  - `service/account/AccountFactory.java` constructs domain objects — it's a good entry point for integration changes.
  - `AccountSubject` / `AccountObserver` / `EmailNotifier` are how account events propagate to external systems (email simulation).

- **Localization / comments**: Some code contains Arabic comments — treat comments as documentation; code identifiers remain English.

If anything above is unclear or you want the doc expanded with runnable examples (PowerShell vs WSL), tell me which parts and I will iterate.
