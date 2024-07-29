export function extractImageUrl(htmlString) {
    const parser = new DOMParser();
    
    const doc = parser.parseFromString(htmlString, 'text/html');
    
    const imgElement = doc.querySelector('img');
    
    return imgElement ? imgElement.src : null;
  }