package com.auction.common.enums;

public enum Action {
    // Nhóm User
    LOGIN,
    REGISTER,
    LOGOUT,

    // Nhóm Sản phẩm & Phiên đấu giá
    GET_ALL_AUCTIONS,   // Lấy danh sách phiên đấu giá
    GET_AUCTION_DETAIL, // Xem chi tiết một phiên
    ADD_ITEM,           // Seller thêm sản phẩm mới

    // Nhóm Giao dịch
    PLACE_BID,          // Gửi lệnh đặt giá
    NEW_BID_UPDATE,     // Server broadcast có bid mới (Realtime)
    AUCTION_ENDED,      // Thông báo phiên đấu giá kết thúc

    // Nhóm Lỗi
    ERROR
}
