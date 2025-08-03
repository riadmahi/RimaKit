# RimaKit — Beautiful UI Components for Compose Multiplatform

**RimaKit** is a sleek and minimal Kotlin Multiplatform UI library built with [JetBrains Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).  
It includes handcrafted UI components designed for delightful cross-platform experiences — from swipeable Tinder-style cards to animated number flows and custom tab bars.

---

## ✨ Features

- Swipeable cards with smooth animations
- Animated number transitions
- Custom tab systems with icons
- Toggle switches with personality
- Clean design, fully composable and customizable


## 📦 Installation

Make sure your project is set up for Compose Multiplatform (Android, iOS, Desktop).

> Gradle/TOML integration coming soon.  
> In the meantime, copy components directly into your shared `commonMain` module.


## Components

### RimaToggle

A customizable animated toggle switch for Compose.

```kotlin
RimaToggle(
    checked = isEnabled,
    onCheckedChange = { isEnabled = it }
)
```

Supports:
- Sizes: `width`, `height`, `thumbSize`
- Colors: `activeColor`, `inactiveColor`, `thumbColor`
- Disabled state

### SwipeCard

A swipeable stack of cards with autoplay and swipe detection.

```kotlin
SwipeCard(
    images = imageList,
    onSwipe = { direction, swipedUrl -> /* handle swipe */ }
)
```

Supports:
- `cardSize`, `loop`, `autoplayDelayMillis`

### TinderSwipeCard

An animated Tinder-style card swiper with autoplay support.

```kotlin
TinderSwipeCard(
    images = imageList,
    onSwipe = { direction, swipedUrl -> /* handle swipe */ }
)
```

Same props as `SwipeCard`.

### MinimalCard

A simple card UI to display an image, title and description.

```kotlin
MinimalCard(
    imageUrl = "https://your-image",
    title = "Card Title",
    description = "Card description"
)
```

Use `MinimalCardData.kt` to define your data class.

### ExpandedTabs

An animated horizontal tab bar using `TabItem` and `TabIcon`.

```kotlin
ExpandedTabs(
    tabs = listOf(
        TabItem(icon = { TabIcon(icon = Icons.Default.Home) }, label = "Home"),
        TabItem(icon = { TabIcon(icon = Icons.Default.Settings) }, label = "Settings")
    ),
    selectedTabIndex = currentIndex,
    onChange = { currentIndex = it }
)
```

### RimaButton

A rounded button with optional border and loading indicator.

```kotlin
RimaButton(
    text = "Start Now",
    onClick = { /* handle action */ },
    isLoading = false
)
```

### AnimatedNumberRandom

Displays an animated number with positive/negative coloring.

```kotlin
AnimatedNumberRandom(
    value = 128.4,
    diff = -7.6
)
```

Props:
- `currencySymbol`
- `positiveColor`, `negativeColor`

## 🚧 Roadmap

- [ ] Add dark mode support
- [ ] Add more layout and animation utilities


## 💡 Why RimaKit?

RimaKit was born from a desire to make Compose Multiplatform apps not just functional, but beautiful.  
It’s inspired by premium design systems, iOS fluidity, and developer ergonomics.

## 🤝 Contributing

Pull requests are welcome! If you have a component or idea to add, feel free to contribute.


## 📄 License

MIT © [Riad Mahi](https://www.linkedin.com/in/riadmahi)


## 🏁 Let’s build better UIs, together.