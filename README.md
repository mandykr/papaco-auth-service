## 용어
### 계정
| 한글명 | 영문명 | 설명 |
| --- | --- | --- |
| 계정명 | userName | OAuth2 Provider 에서 사용하는 식별자 (ex. 955620) |
| 사용자명 | name | OAuth2 Provider 에 등록된 사용자명 (ex. mandykr) |


## 모델링
### 계정(`Account`)
- 계정은 식별자와 계정명, 사용자명, 이메일, 사용자 권한을 가진다.

#### OAuth2
```mermaid
sequenceDiagram
  actor User
    autonumber
        User ->> Gateway : Access Application
		Gateway ->> Auth Service : Authenticate
		Auth Service ->> Github OAuth Server : Authenticate
		Github OAuth Server ->> User : Redirect Login Page
		User ->> Github OAuth Server : Login
		Github OAuth Server ->> User : Return Authorization Code
		User ->> Gateway : Redirect Authorization Code
		User ->> Auth Service : Redirect Authorization Code
		Auth Service ->> Github OAuth Server : Request Token And OAuth2User
```

#### Account
```mermaid
sequenceDiagram
    autonumber
        OAuth2UserService ->> LoginAccountService : Save Account(OAuth2User)
		LoginAccountService ->> AccountEventPublisher : publish AccountEvent
```

#### Account Event
```mermaid
sequenceDiagram
    autonumber
        OutboxService ->> OutboxRepository : Save Outbox(AccountEvent)
		MessageRelayService ->> AccountProducer : Send Outboxes
```
