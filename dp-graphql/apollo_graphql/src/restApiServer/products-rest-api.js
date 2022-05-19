import {RESTDataSource} from 'apollo-datasource-rest';

class productsRestApi extends RESTDataSource {
    constructor() {
        super();
        // dev for Local
            this.baseURL = 'http://localhost:8085';
        // dev for IDE
            // this.baseURL = 'http://8085-ide-xxxxxxxxxx.kuberez.io'
        // prod
        //     this.baseURL = 'http://product:8080';
    }

    async getProducts() {
        const data = await this.get('/products', {})
        var value = this.stringToJson(data);
        // return retunVal
        return value._embedded.products;
    }

    // GET
    async getProduct(id) {
        const data = await this.get(`/products/${id}`, {})
        var value = this.stringToJson(data);
        return value;
    }

    stringToJson(str){
        if(typeof str == 'string'){
            str = JSON.parse(str);
        }
        return str;
    }
}

export default productsRestApi;



