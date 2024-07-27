# Alezhu ClassicUI

<!-- Plugin description -->
Based on original [JetBrains ClassicUI Plugin](https://plugins.jetbrains.com/plugin/24468-classic-ui).

**In difference from original plugin this one has no limits to version.**

Provides the classic JetBrains IDE look and feel, which was used before the introduction of <a href="https://www.jetbrains.com/idea/new-ui">New UI</a>.
Includes UI layout, 3 tool window stripes and icons for IDEs and plugins.
If you prefer the old-school layout and visual style, this plugin brings back that familiar look and feel.

<!-- Plugin description end -->

## Installation

  Download the [latest release](https://github.com/alezhu/classic-ui/releases/latest) and install it manually using
  <kbd>Settings/Preferences</kbd> > <kbd>Plugins</kbd> > <kbd>⚙️</kbd> > <kbd>Install plugin from disk...</kbd>

## Origin

JetBrains makes great products. But their NewUI is complete suxx. And if earlier it had to be specially enabled, then in the latest versions of their IDEs it is enabled by default. And to disable it, you need not just change something in the settings, no - you need to install a special ClassicUI plugin. Moreover, this plugin supported only a specific version of the IDEA platform. And if any JetBrains IDE is updated, the plugin becomes invalid, the IDE starts with NewUI enabled. You need to update the plugin manually each time and restart the IDE again to disable it again. JetBrains probably thinks that the more often we see NewUI, the more likely we are to love it. They are wrong, but that's their business.

I looked at how the ClassicUI plugin is made. And I realized that it... does not contain anything. Not a line of code. Only a specific declaration in plugin.xml. That is, the entire old UI is initially in the IDE. But instead of just being able to enable it in the settings, JetBrains makes things more complicated.

Then I wrote this plugin. It's almost a complete copy of the JetBrains ClassicUI plugin, but its difference is that it works with any version of the IDEA platform from version 232 (2023.2) to version 299 (it will probably be 2029.9 someday, I guess). The old UI won't disable after the next IDE update. At least until JetBrains completely removes the old UI from their products.

---
Plugin based on the [IntelliJ Platform Plugin Template][template].

[template]: https://github.com/JetBrains/intellij-platform-plugin-template
