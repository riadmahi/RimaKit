
# RimaKit — Beautiful UI Components for Compose Multiplatform

**RimaKit** is a sleek and minimal Kotlin Multiplatform UI library built with [JetBrains Compose Multiplatform](https://www.jetbrains.com/lp/compose-multiplatform/).  
It includes handcrafted UI components designed for delightful cross-platform experiences — from swipeable Tinder-style cards to animated number flows and custom tab bars.

---

## ✨ Features

- 🃏 Swipeable cards with smooth animations
- 🔢 Animated number transitions
- 🧲 Custom tab systems with icons
- 🎛️ Toggle switches with personality
- 🎨 Clean design, fully composable and customizable

---

## 📦 Installation

Make sure your project is set up for Compose Multiplatform (Android, iOS, Desktop).

> Gradle/TOML integration coming soon.  
> In the meantime, copy components directly into your shared `commonMain` module.

---

## 📚 Components

### 🔘 RimaToggle

A sleek, animated toggle switch with Compose beauty.  
Customize colors, shapes, and transitions.

```kotlin
RimaToggle(
    checked = isEnabled,
    onCheckedChange = { isEnabled = it }
)
```

---

### 🃏 SwipeCard

A card deck UI with smooth swipe gestures, perfect for Tinder-like interfaces.

```kotlin
SwipeCard(
    items = cardList,
    onSwiped = { direction, index -> ... },
    content = { cardData -> MinimalCard(cardData) }
)
```

---

### ❤️ TinderSwipeCard

An enhanced version of `SwipeCard`, including pre-built stack layout and direction tracking.

```kotlin
TinderSwipeCard(
    dataList = myCards,
    onSwipeComplete = { direction, card -> ... }
)
```

---

### 🧾 MinimalCard + MinimalCardData

A sample minimalistic card UI for quick integration and testing.

```kotlin
MinimalCard(data = MinimalCardData(title = "Hello", subtitle = "World"))
```

---

### 📂 ExpandedTabs + TabItem + TabIcon

A beautiful, animated tab system with custom icons and labels.

```kotlin
ExpandedTabs(
    items = listOf(
        TabItem(icon = { TabIcon(icon = Icons.Default.Home) }, label = "Home"),
        TabItem(icon = { TabIcon(icon = Icons.Default.Settings) }, label = "Settings")
    ),
    selectedIndex = selectedTab,
    onSelectedIndexChange = { selectedTab = it }
)
```

---

### 🎯 RimaButton

A minimal button with consistent styling and animations.

```kotlin
RimaButton(
    text = "Get Started",
    onClick = { /* action */ }
)
```

---

### 🔢 AnimatedNumberRandom

A fun animated number that updates randomly with smooth transitions.

```kotlin
AnimatedNumberRandom(
    targetNumber = 42,
    modifier = Modifier.padding(16.dp)
)
```

---

## 🚧 Roadmap

- [ ] Add dark mode support
- [ ] Add more layout and animation utilities

---

## 💡 Why RimaKit?

RimaKit was born from a desire to make Compose Multiplatform apps not just functional, but beautiful.  
It’s inspired by premium design systems, iOS fluidity, and developer ergonomics.

---

## 🤝 Contributing

Pull requests are welcome! If you have a component or idea to add, feel free to contribute.

---

## 📄 License

MIT © [Riad Mahi](https://www.linkedin.com/in/riadmahi)

---

## 🏁 Let’s build better UIs, together.