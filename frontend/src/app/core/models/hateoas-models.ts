
export interface Link {
  href: string;
}

// EntityModel flattens your DTO properties and adds _links
export type EntityModel<T> = T & {
  _links?: {
    [rel: string]: Link;
  };
};

// CollectionModel wraps a list of items inside _embedded and adds _links
export interface CollectionModel<T> {
  _embedded?: {
    // The key here will usually be the collection name, e.g., 'persons'
    [rel: string]: T[];
  };
  _links?: {
    [rel: string]: Link;
  };
}

