export interface OrderDTO {
  id: string;
  orderDescription: string;
  date: Date;
  amount: number;
  address: string;
  payment: string;
  orderStatus: OrderStatus;
  totalAmount: number;
  discount: number;
  trackingId: string;
  userName: string;
  cartItems: CartItemsDTO[];
  couponName: string;
  processedImg:string;
}
export interface AddProductInCartDTO {
  userId: number;
  productId: number;
}
export interface CartItemsDTO {
  id: number;
  price: number;
  quantity: number;
  productId: number;
  orderId: number;
  productName: string;
  returnedImg: Uint8Array;
  userId: number;
}
export interface CategoryDTO {
  id: number;
  name: string;
  description: string;
}

export interface CouponDTO {
  name: string;
  code: string;
  discount: number;
  expirationDate: Date;
}

export interface LoginDTO {
  email: string;
  password: string;
}

export interface PlaceOrderDTO {
  id: string;
  userId: string;
  address: string;
  orderDescription: string;
}
export interface ProductDTO {
  id: number;
  name: string;
  price: number;
  description: string;
  byteImage: Uint8Array;
  categoryId: number;
  categoryName: string;
  processedImg:string;
  image: File;
}
export interface RegisterDTO {
  email: string;
  password: string;
  name: string;
}
export interface UserDTO {
  id: number;
  email: string;
  name: string;
  userRole: UserRole;
}
export interface CartItems {
  id: string;
  price: number;
  quantity: number;
  product: Product;
  user: User;
  order?: Order;
  processedImg:string;
  productId:string;
}
export interface Category {
  id: number;
  name: string;
  description: string;
}
export interface Coupon {
  id: number;
  name: string;
  code: string;
  discount: number;
  expirationDate: Date;
}
export interface Order {
  orderDescription: string;
  date: Date;
  amount: number;
  address: string;
  payment: string;
  orderStatus: OrderStatus;
  totalAmount: number;
  discount: number;
  trackingId: string;
  user: User;
  coupon?: Coupon;
  cartItems?: CartItems[];
  processedImg:string;
}
export interface Product {
  id: string;
  name: string;
  price: number;
  description: string;
  image: Uint8Array;
  category: Category;
  categoryName:string;
  processedImg:string;
}
export interface User {
  email: string;
  password: string;
  name: string;
  role: UserRole;
}
export enum OrderStatus {
  Pending = 'Pending',
  Placed = 'Placed',
  Shipped = 'Shipped',
  Delivered = 'Delivered'
}
export enum UserRole {
  ADMIN = 'ADMIN',
  CUSTOMER = 'CUSTOMER'
}

export interface savedUser {
  userID: string;
  email: string;
  password: string;
  name: string;
  role: UserRole;
}
