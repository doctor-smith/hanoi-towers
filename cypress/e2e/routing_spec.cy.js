describe('Routing Test after Refresh', () => {
    it('should stay on the same page after refreshing', () => {
      // Navigate to a specific route
      cy.visit('http://localhost:8080/game');
  
      // Verify that the correct page is loaded
      cy.url().should('include', 'http://localhost:8080/game');
  
      // Refresh the page
      cy.reload();
  
      // Verify that the same page is still loaded
      cy.url().should('include', 'http://localhost:8080/game');
    });
  });
  