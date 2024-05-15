# MyRes
MyRes is a restaurant management application to help customers, waiters and restaurant owners through mobile applications to manage restaurants, menus and allow customers to order online.

#### User role
- Client
- Waitress
- Restaurant Owner
- Kitchen Worker

| Page Name          | XML Layout File                   | Activity Name                | Purpose                                                        |
|--------------------|-----------------------------------|------------------------------|----------------------------------------------------------------|
| Client Register Page| `activity_client_register.xml`     | `ClientRegisterActivity`      | Client registration, including form fields for name, phone, gender, email, and password |
| Login Page         | `activity_login.xml`               | `LoginActivity`              | Login page with email, password fields, and role selection, redirects to respective home page |
| Client Home Page   | `activity_client_home.xml`         | `ClientHomeActivity`         | Displays restaurant list with search and sorting options       |
| Restaurant Detail Page| `activity_restaurant_detail.xml` | `RestaurantDetailActivity`   | Shows restaurant details including navigation and menu buttons |
| Menu Page          | `activity_menu.xml`                | `MenuActivity`               | Displays menu items, allows adding notes and placing orders    |
| Order Tracking Page| `activity_order_tracking.xml`      | `OrderTrackingActivity`      | Displays order status and preparation time countdown           |
| Payment Page       | `activity_payment.xml`             | `PaymentActivity`            | Simulates payment functionality and shows total order price    |
| Review Page        | `activity_review.xml`              | `ReviewActivity`             | Allows clients to leave reviews after payment                  |
| Service Request Page| `activity_service_request.xml`    | `ServiceRequestActivity`     | Allows clients to request service, notifies waitresses         |
| Restaurant Owner Home Page| `activity_owner_home.xml`   | `OwnerHomeActivity`          | Restaurant owner home page with management functionalities     |
| Add Restaurant Page| `activity_add_restaurant.xml`      | `AddRestaurantActivity`      | Form for adding restaurant details and building menu           |
| Update Restaurant Detail Page| `activity_update_restaurant.xml`| `UpdateRestaurantActivity` | Allows restaurant owners to update restaurant details          |
| Menu Management Page| `activity_menu_management.xml`    | `MenuManagementActivity`     | Allows adding, updating, or deleting menu items                |
| Add Waitress Page  | `activity_add_waitress.xml`        | `AddWaitressActivity`        | Form for creating a new waitress account                       |
| Add Kitchen Worker Page| `activity_add_kitchen_worker.xml`| `AddKitchenWorkerActivity`  | Form for creating a new kitchen worker account                 |
| View Reviews Page  | `activity_view_reviews.xml`        | `ViewReviewsActivity`        | Allows restaurant owners to view client reviews and contact information |
| Waitress Home Page | `activity_waitress_home.xml`       | `WaitressHomeActivity`       | Waitress home page with order list and service request notifications |
| Kitchen Worker Home Page| `activity_kitchen_worker_home.xml`| `KitchenWorkerHomeActivity`| Kitchen worker home page with order list and status updates    |
