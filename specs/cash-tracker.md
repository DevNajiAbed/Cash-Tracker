# Cash Track Portfolio Spec — Agent Guide

## What this repo is

This is a **design specification** for the **Cash Track** Android expense-tracking app. It is not the app codebase — it defines the UI, screens, features, and tech stack that should be built.

## Files

| File | Purpose |
|---|---|
| `specs/cashtrack_portfolio_spec.md` | Full app specification (design system, 17 screens, features, tech stack) |
| `index.html` | Visual spec page — single-file HTML that renders the spec as a design reference |
| `AGENTS.md` | This file |
| Font setup notes | Typography section in both files now includes implementation instructions for adding Inter/Poppins to the Android project via downloadable Google Fonts |

## Visual spec page (`index.html`)

Self-contained HTML page (zero dependencies, just a Google Fonts link). Open in any browser.

- **Live dark/light toggle** — uses the exact dark/light hex palettes from the spec, persisted to localStorage
- **Color scheme** organized by **Material3 color roles** (Primary, On Primary, Primary Container, Secondary, Tertiary, Error, Surface, Outline, etc.) — each role shows light and dark values side by side with swatches
- **Typography** mapped to the **Material3 type scale** (Display Small, Headline Large, Title Large, Body Large, Label Medium, etc.) with spec sizes
- **UI components** section with visual mockups of buttons, cards, chips, inputs, and charts at spec dimensions
- **17 phone mockups** — each screen rendered inside a phone frame (340x690px, notch, status bar): Splash, Onboarding (3 steps), Register, Home Dashboard, Add Transaction (Income + Expense), Transactions List, Analytics, Categories, Add Category, Budgets, Add Budget, Settings, Profile/About, Edit Profile
- **Bottom navigation bar** — 4-tab nav (Home, Transactions, Analytics, Settings) on Dashboard, Transactions List, Analytics, and Settings mockups; the active tab is highlighted per screen
- **Navigation Flow section** — diagram showing the complete screen-to-screen navigation map with branches for first launch vs returning user, sub-screens organized by parent tab
- **Dashboard nav cards** — Categories and Budgets cards replaced the "Monthly Spending" chart on the Home Dashboard, linking to those sub-screens
- **Tech stack chips** — styled per the spec's chip design (999px radius)

## Tech stack (from spec)

| Layer | Choice |
|---|---|
| UI | Jetpack Compose |
| Architecture | MVVM + Clean Architecture + Repository Pattern |
| Database | Room |
| State | StateFlow / Flow |
| DI | Hilt |
| Charts | Android charts library or custom |
| Networking | Retrofit (optional, later) |

## Key design constants (from spec)

- **Primary:** `#4F46E5`, **Secondary:** `#10B981`, **Tertiary:** `#F59E0B`, **Error:** `#EF4444`
- **Font:** Inter
- **Corner radius:** buttons/cards 16dp, inputs 16dp, chips 999dp, cards 20dp
- **Dark mode colors** and full M3 token set (on-colors, containers, surface variants, outlines) defined in `index.html`

## What's NOT here

- No code, build system, tests, or CI
- No git history or repository initialized
- No `README.md` — the spec document and HTML page serve as the reference

## When building Cash Track from this spec

Create a new Android project and implement the screens in order:
Splash → Onboarding (3 steps) → Register → Home Dashboard → Add Transaction (Income + Expense) → Transactions List → Analytics → Categories → Add Category → Budgets → Add Budget → Settings → Profile/About → Edit Profile
