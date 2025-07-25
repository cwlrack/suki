# Technical Architecture

Suki's technical architecture is designed to support a smooth, responsive, and secure user experience while allowing future scalability and feature expansion.

## Platform & Framework
* Native mobile apps built separately for Android (Kotlin) and iOS (Swift) to maximize performance, responsiveness, and platform-specific UI best practices.

## Local Data Storage
* Utilizes SQLite or Realm for structured, efficient local data storage of moods, tags, photos, and pet state.
* Photos and media stored securely in the device's file system with references in the database.

## Encryption
* All sensitive data, including backups and local storage, is encrypted at rest using platform-supported encryption APIs.
* End-to-end encryption for cloud backups ensures user privacy.

## Cloud Services (Premium)
* Optional encrypted backups and sync use secure cloud providers (e.g., AWS S3, Firebase with encryption layer) with zero-knowledge encryption.
* Sync architecture supports conflict resolution for multi-device use.

## UI & UX
* Lightweight, fluid animations and responsive layouts tailored for mobile devices.
* Widget support implemented natively to integrate with home screen widgets on both platforms.

## Modular Design
* Core app logic decoupled from UI for easier maintenance and future feature additions like AI-driven insights or social sharing.

## Security & Privacy Compliance
* Adherence to GDPR, CCPA, and relevant data protection laws, with clear privacy policies and user consent flows.

## Related Features
- [[Backups & Storage]] - Storage implementation
- [[Privacy & Sharing Controls]] - Security measures
- [[Interactive Widgets]] - Widget architecture
- [[Technical Tools]] - Development tools
