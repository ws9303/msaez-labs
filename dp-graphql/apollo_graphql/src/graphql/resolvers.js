const resolvers = {
    Order: {
        delivery: async (root, {deliveryId}, {dataSources}) => {
            try {
                if (root && root._links.self.href) {
                    var parseLink = root._links.self.href.split('/')
                    var getOrderId = parseLink[parseLink.length - 1]
                    var deliveries = await dataSources.deliveryRestApi.getDeliveries();

                    if(deliveries){
                        var rtnVal = null
                        Object.values(deliveries).forEach(function (delivery) {
                            if(delivery && delivery.orderId == getOrderId){
                                rtnVal = delivery
                            }
                        })
                        return rtnVal
                    }
                }
                return null;
            } catch (e) {
                return null;
            }
        },
        deliveries: async (_, __, {dataSources}) => {
            return await dataSources.deliveryRestApi.getDeliveries();
        },
        product: async (root, {productId}, {dataSources}) => {
            if (!productId) productId = root.productId

            if (productId) {
                return await dataSources.productRestApi.getProduct(productId);
            }
            return null;
        },
        products: async (_, __, {dataSources}) => {
            return await dataSources.productRestApi.getProducts();
        }
    },
    Product: {

    },
    Delivery: {
        orders: async (_, __, {dataSources}) => {
            return await dataSources.orderRestApi.getOrders();
        },
        order: async (root, {orderId}, {dataSources}) => {
            if (!orderId) orderId = root.orderId

            if (orderId) {
                return await dataSources.orderRestApi.getOrder(orderId);
            }
            return null;
        },
        product: async (root, {productId}, {dataSources}) => {
            if (!productId) productId = root.productId
            if (productId) {
                return await dataSources.productRestApi.getProduct(productId);
            }
            return null;
        },
        products: async (_, __, {dataSources}) => {
            return await dataSources.productRestApi.getProducts();
        }
    },
    Query: {
        delivery: async (_, {deliveryId}, {dataSources}) => {
            if (deliveryId) {
                return await dataSources.deliveryRestApi.getDelivery(deliveryId);
            }
            return null;
        },
        deliveries: async (_, __, {dataSources}) => {
            return await dataSources.deliveryRestApi.getDeliveries();
        },
        order: async (_, {orderId}, {dataSources}) => {
            if (orderId) {
                return await dataSources.orderRestApi.getOrder(orderId);
            }
            return null;
        },
        orders: async (_, __, {dataSources},info) => {
            console.log('orders-info',info)
            return await dataSources.orderRestApi.getOrders();
        },
        product: async (_, {productId}, {dataSources}) => {
            if (productId) {
                return await dataSources.productRestApi.getProduct(productId);
            }
            return null;

        },
        products: async (_, __, {dataSources}) => {
            return await dataSources.productRestApi.getProducts();
        }
    }
};


export default resolvers;
