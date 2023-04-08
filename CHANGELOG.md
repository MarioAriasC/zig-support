<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# zig-support Changelog

## [Unreleased]

## [0.2.4] - 2023-04-08

### Added
- Support for the new IDEA platform version 2023.1
- Some auto-completions have new icons
- Background work to support features for new versions
- Fix building process by updating images to Java 17

## [0.2.2] - 2022-12-03

### Added
- Support for the new IDEA platform version 2022.3

## [0.2.1]

### Added
- Autocomplete builtin functions, i.e. `@functions`
- Autocomplete primitive types

## [0.2.0]

### Added
- - `packed` structs with backing field, e.g.: `packed struct(i32)`
  - `inline` switch cases
  - Remove `anytype` fields

## [0.1.0]

### Added
- [#24](https://github.com/MarioAriasC/zig-support/issues/24) Use `zig fmt` to format `*.zig` files. You can use
  the `Reformat code` action (By default `Cmd/Ctrl + Alt + L`)
- Updated to the latest JetBrain's IntelliJ platform plugin version.

## [0.0.7]

### Added
- - `St` -> `[]const u8`
  - `tst` -> Creates a test: `test "" {}`
  - `csd` -> Constant `Self` declaration: `const Self = @This();`
  - `ced` -> Enum
  - `cstd` -> Struct
  - `fn0` -> Function without parameters
  - `fn1` -> Function with one parameter
  - `fn2` -> Function with two parameters
- Support for the new IDEA platform version 2022.2

## [0.0.6]

### Added
- Improved parser: 100% of the language is parsed correctly.
- Insert closing pair match for `(`, `{`, `[` and `"`
- Highlight `(`, `{` and `[` pairs
- - `import` -> Create an import using the `@import` function
  - `sdp` -> Debug Print statement
  - `sli` -> Log Info statement

### Fixed
- Fix issue [#15](https://github.com/MarioAriasC/zig-support/issues/15)

## [0.0.5]

### Fixed
- Fix issue [#12](https://github.com/MarioAriasC/zig-support/issues/12)

## [0.0.4]

### Added
- Improve parser: `asm` expressions parsed correctly

### Fixed
- Support for the new IDEA platform version (211 to 221).

## [0.0.3]

### Fixed
- Support for the new IDEA platform version (211 and onwards).
- Fix issue [#7](https://github.com/MarioAriasC/zig-support/issues/7)

## [0.0.2]

### Added
- Improve parser: More language constructs are supported
- Support for 0.9.0 - Saturating arithmetic operators (`+|`,`-|`,`*|`,`<<|`,`+|=`,`-|=`,`*|=` and `<<|=`)
- Support for 0.9.0 - Move `false`, `true`, `null` and `undefined` out of the keywords
- Implement Line Comment/Uncomment

### Known issues
- Sometimes big files (More than 1000 declarations) failed to parser with an error
  message `Maximum recursion level (1,000) reached`, You can fix it by adding the
  property `-Dgrammar.kit.gpub.max.level=2000` to your `idea.vmoptions` file or [equivalent](https://www.jetbrains.com/help/idea/tuning-the-ide.html)

## [0.0.1]

### Added
- Initial parser and lexer implementation
- Initial scaffold created
  from [IntelliJ Platform Plugin Template](https://github.com/JetBrains/intellij-platform-plugin-template)

### Known issues
- - ~~`\\` Line strings sometimes aren't parsed correctly.~~. Fixed on version 0.0.6 
    - ~~`asm volatile` sometimes aren't parsed correctly.~~. Fixed on version 0.0.4

[Unreleased]: https://github.com/MarioAriasC/zig-support/compare/v0.2.4...HEAD
[0.2.4]: https://github.com/MarioAriasC/zig-support/compare/v0.2.2...v0.2.4
[0.2.2]: https://github.com/MarioAriasC/zig-support/compare/v0.2.1...v0.2.2
[0.2.1]: https://github.com/MarioAriasC/zig-support/compare/v0.2.0...v0.2.1
[0.2.0]: https://github.com/MarioAriasC/zig-support/compare/v0.1.0...v0.2.0
[0.1.0]: https://github.com/MarioAriasC/zig-support/compare/v0.0.7...v0.1.0
[0.0.7]: https://github.com/MarioAriasC/zig-support/compare/v0.0.6...v0.0.7
[0.0.6]: https://github.com/MarioAriasC/zig-support/compare/v0.0.5...v0.0.6
[0.0.5]: https://github.com/MarioAriasC/zig-support/compare/v0.0.4...v0.0.5
[0.0.4]: https://github.com/MarioAriasC/zig-support/compare/v0.0.3...v0.0.4
[0.0.3]: https://github.com/MarioAriasC/zig-support/compare/v0.0.2...v0.0.3
[0.0.2]: https://github.com/MarioAriasC/zig-support/compare/v0.0.1...v0.0.2
[0.0.1]: https://github.com/MarioAriasC/zig-support/commits/v0.0.1
