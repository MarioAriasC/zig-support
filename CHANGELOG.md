<!-- Keep a Changelog guide -> https://keepachangelog.com -->

# zig-support Changelog

## [Unreleased]

## [0.0.6]
### Added
- Improved parser: 100% of the language is parsed correctly.
- Insert closing pair match for `(`, `{`, `[` and `"`
- Highlight `(`, `{` and `[` pairs
- Introduce Live Templates for Zig:
  - `import` -> Create an import using the `@import` function
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
- There are still certain problems with the parser
    - ~~`\\` Line strings sometimes aren't parsed correctly.~~. Fixed on version 0.0.6 
    - ~~`asm volatile` sometimes aren't parsed correctly.~~. Fixed on version 0.0.4